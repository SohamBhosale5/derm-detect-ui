package com.skin.skinapp2.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.pytorch.IValue;
import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;
import org.pytorch.MemoryFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.appcompat.app.AppCompatActivity;
import com.skin.skinapp2.R;
import com.skin.skinapp2.databinding.FragmentHomeBinding;

import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;
import static org.pytorch.LiteModuleLoader.load;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    View root;
    ImageView imageView;
    Button button;
    Module module = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        root= inflater.inflate(R.layout.fragment_home,container,false);
        if((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        } else {
            System.out.println("Permissions Granted");
        }
        imageView=root.findViewById(R.id.captureImage);
        button=root.findViewById(R.id.camera_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //  @Override
        //public void onChanged(@Nullable String s) {
        //  textView.setText(s);
        //}
        //});
        return root;
    }

    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }

    private void selectImage() {
        try {
            // creating bitmap from packaged into app android asset 'image.jpg',
            // app/src/main/assets/image.jp
            // loading serialized torchscript module from packaged into app android asset model.pt,
            // app/src/model/assets/model.pt
            //System.out,println(assetFilePath(getActivity(), "mobilenet1.pt"))
            //System.out.println(assetFilePath(getActivity(), "mobilenet1.pt"));
            module = load(assetFilePath(getActivity(), "mobilnetv1.ptl"));
        } catch (IOException e) {
            Log.e("PytorchHelloWorld", "Error reading assets", e);
        }
        final CharSequence options [] = {"Take Picture", "Choose From Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(options[item].equals("Take Picture")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if(options[item].equals("Choose From Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("On Activity Result");
        System.out.println("Request Code: " + requestCode + "Result Code: " + resultCode + "Data: " + data);
        final Tensor inputTensor;
        try {
            if(requestCode == 1 && resultCode == RESULT_OK) {
                System.out.println("On Activity Result");
                Bitmap image = (Bitmap) data.getExtras().get("data");
                System.out.println("Image Data: " + image);
                imageView.setImageBitmap(image);
                inputTensor = TensorImageUtils.bitmapToFloat32Tensor(image,
                        TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB, MemoryFormat.CHANNELS_LAST);
            } else {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePath,null, null,null);
                cursor.moveToFirst();
                int index = cursor.getColumnIndex(filePath[0]);
                String path = cursor.getString(index);
                cursor.close();
                imageView.setImageBitmap(BitmapFactory.decodeFile(path));
                inputTensor = TensorImageUtils.bitmapToFloat32Tensor(BitmapFactory.decodeFile(path),
                        TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB, MemoryFormat.CHANNELS_LAST);

            }
            // running the model
            final Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();

            // getting tensor content as java array of floats
            final float[] scores = outputTensor.getDataAsFloatArray();
            for(int i = 0; i < scores.length; i++) {
                System.out.println("scores " + scores[i]);
            }
            float maxScore = -Float.MAX_VALUE;
            int maxScoreIdx = -1;
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] > maxScore) {
                    maxScore = scores[i];
                    maxScoreIdx = i;
                }
            }

            String className = SkinClasses.SKIN_CLASSES[maxScoreIdx];
            System.out.println(className);
        } catch(Exception E) {
            E.printStackTrace();
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }
}

