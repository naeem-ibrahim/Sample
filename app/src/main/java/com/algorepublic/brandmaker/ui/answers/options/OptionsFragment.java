package com.algorepublic.brandmaker.ui.answers.options;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.algorepublic.brandmaker.BuildConfig;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentAttemptAnswersBinding;
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.model.QuestionsModel;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;
import com.algorepublic.brandmaker.utils.CircleTransform;
import com.algorepublic.brandmaker.utils.Helper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created By apple on 2019-08-02
 */
public class OptionsFragment extends Fragment {

    private FragmentAttemptAnswersBinding binding;
    private OptionViewModel viewModel;

    private int currentQuestion = 0;
    File file = null;
    String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CHOOSE_PHOTO = 2;
    private static final int REQUEST_PERMISSION = 1111;

    private int brandStatementID = 0;
    private ArrayList<QuestionsModel> questionsList = new ArrayList<>();
    private ArrayList<QuestionsModel> answersList = new ArrayList<>();

    private ProgressDialog mProgressDialog;
    public static OptionsFragment getInstance(int brandStatementID) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putInt("BrandStatementID", brandStatementID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_attempt_answers, container, false);
        viewModel = ViewModelProviders.of(this).get(OptionViewModel.class);
        questionsList = new ArrayList<>();
        answersList = new ArrayList<>();
        brandStatementID = getArguments().getInt("BrandStatementID");

        binding.tvPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            REQUEST_PERMISSION);
                } else {
                    choose();
                }
            }
        });


        viewModel.getQuestionObservable().observe(this, baseResponse -> {
            if (baseResponse != null) {
                if (baseResponse.isSuccess() && baseResponse.getData() != null) {
                    if (baseResponse.getData().getQuestions().size() > 0) {
                        questionsList.clear();
                        questionsList.addAll(baseResponse.getData().getQuestions());
                        setQuestion();
                    } else {
//                        binding.tvEmpty.setText("No Question for this statement");
//                        binding.tvEmpty.setVisibility(View.VISIBLE);
                    }
                } else {
                    Helper.snackBarWithAction(binding.getRoot(), getActivity(), baseResponse.getMessage());
                }
            }
        });


        viewModel.getQuestionsApi(brandStatementID);


        return binding.getRoot();
    }

    private void setQuestion() {
        QuestionsModel questionsModel = questionsList.get(currentQuestion);

        binding.tvQuestion.setText(questionsModel.getStatement());

        if (questionsModel.isTakePictures()) {
            binding.photoCardView.setVisibility(View.VISIBLE);
        }

        binding.btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionsModel.setAnswer("Yes");
                if (questionsModel.isTakePictures()) {
                } else {
                    loadNext(questionsModel);
                }
            }
        });

        binding.btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionsModel.setAnswer("No");
                if (questionsModel.isTakePictures()) {
                } else {
                    loadNext(questionsModel);
                }
            }
        });


        binding.saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionsModel.setImage(file);
                loadNext(questionsModel);
            }
        });
    }

    private void loadNext(QuestionsModel questionsModel) {
        answersList.add(questionsModel);

        Picasso.get()
                .load("https:url//empty")
                .fit()
                .placeholder(R.drawable.ic_mail)
                .into(binding.answerPhoto);

        currentQuestion++;
        if (currentQuestion < questionsList.size()) {
            binding.photoCardView.setVisibility(View.GONE);
            setQuestion();
        } else {
            binding.photoCardView.setVisibility(View.GONE);
            binding.optionsCardView.setVisibility(View.GONE);
            binding.descriptionCardView.setVisibility(View.GONE);
            binding.doneCardView.setVisibility(View.VISIBLE);
        }

        viewModel.getUploadingAnswersObservable().observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                mProgressDialog.dismiss();
                ((MainActivity) getContext()).onBackPressed();
            }
        });

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog = ProgressDialog.show(getContext(), "", "Loading Please wait...", true);
                Log.e("ArrayList", "" + answersList.size());
                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.uploadAnswers(brandStatementID, answersList);
                    }
                },2000);
            }
        });
    }

    private void choose() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setCancelable(false);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    try {
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    1112);
                        } else {
                            dispatchTakePictureIntent();
                        }
                    } catch (IOException e) {
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                // Show the thumbnail on ImageView
                Uri imageUri = Uri.parse(mCurrentPhotoPath);
                if (imageUri != null) {
                    if (imageUri.getPath() != null) {
                        file = new File(imageUri.getPath());
                        Picasso.get()
                                .load(file)
                                .fit()
                                .placeholder(R.drawable.ic_mail)
                                .into(binding.answerPhoto);
//                        uploadToServer(file);
                    }
                }
            } else if (requestCode == 2) {
                if (data != null) {
                    String path = Helper.getRealPathFromURI(getContext(), data.getData());
                    if (path != null) {
                        file = new File(path);
                        if (file.exists()) {
                            Picasso.get()
                                    .load(file)
                                    .fit()
                                    .placeholder(R.drawable.ic_mail)
                                    .into(binding.answerPhoto);
//                            uploadToServer(file);
                        } else {
                            Uri selectedImageUri = data.getData();
                            if (selectedImageUri != null) {
                                if (selectedImageUri.getPath() != null) {
                                    file = new File(selectedImageUri.getPath());
                                    if (file.exists()) {
                                        Picasso.get()
                                                .load(file)
                                                .transform(new CircleTransform())
                                                .placeholder(R.drawable.ic_mail)
                                                .into(binding.answerPhoto);
//                                        uploadToServer(file);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                choose();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        REQUEST_PERMISSION);
            }
        }
    }
}
