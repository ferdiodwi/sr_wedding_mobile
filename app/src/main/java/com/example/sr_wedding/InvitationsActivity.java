package com.example.sr_wedding;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InvitationsActivity extends AppCompatActivity {

    private TextView invitationTextView;
    private EditText guestNameEditText;
    private ImageView brideImageView, groomImageView;
    private String brideName;
    private String groomName;

    private String selectedImageType; // To store whether bride or groom image is selected
    private boolean isBrideImageSelected = false; // Flag to check if bride image is selected
    private boolean isGroomImageSelected = false; // Flag to check if groom image is selected

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);

        // Get bride's and groom's names from intent
        brideName = getIntent().getStringExtra("BRIDE_NAME");
        groomName = getIntent().getStringExtra("GROOM_NAME");

        // Initialize UI elements
        invitationTextView = findViewById(R.id.invitationTextView);
        guestNameEditText = findViewById(R.id.guestNameEditText);
        brideImageView = findViewById(R.id.brideImageView);
        groomImageView = findViewById(R.id.groomImageView);

        Button sendInvitationButton = findViewById(R.id.sendInvitationButton);
        Button selectBrideImageButton = findViewById(R.id.selectBrideImageButton);
        Button selectGroomImageButton = findViewById(R.id.selectGroomImageButton);

        // Set up onClickListener for send invitation button
        sendInvitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guestName = guestNameEditText.getText().toString();

                // Check if both images are selected
                if (isBrideImageSelected && isGroomImageSelected) {
                    String invitationMessage = generateInvitationMessage(guestName);
                    displayInvitation(invitationMessage);
                } else {
                    Toast.makeText(InvitationsActivity.this, "Please select both images before sending the invitation.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up onClickListener for selecting bride image
        selectBrideImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageType = "bride"; // Set the selected image type
                openImageChooser();
            }
        });

        // Set up onClickListener for selecting groom image
        selectGroomImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageType = "groom"; // Set the selected image type
                openImageChooser();
            }
        });
    }

    // Method to generate invitation message
    private String generateInvitationMessage(String guestName) {
        return "Dear " + guestName + ",\n\n" +
                "You are cordially invited to the wedding of " + brideName + " and " + groomName + ".\n" +
                "We look forward to celebrating this special day with you!\n\n" +
                "Best Regards,\n" +
                "The Wedding Planner";
    }

    // Method to open image chooser
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                // Check which image is being set
                if (selectedImageType.equals("bride")) {
                    brideImageView.setImageURI(imageUri);
                    isBrideImageSelected = true; // Set the flag to true
                } else {
                    groomImageView.setImageURI(imageUri);
                    isGroomImageSelected = true; // Set the flag to true
                }
            }
        }
    }


private void createPdf(String invitationMessage) {
    // Create a new PdfDocument
    PdfDocument pdfDocument = new PdfDocument();

    // Create a page description with a wider page size for better flexibility
    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, 700, 1).create();

    // Start a new page
    PdfDocument.Page page = pdfDocument.startPage(pageInfo);

    // Get the canvas of the page
    Canvas canvas = page.getCanvas();

    // Set up paint for the background
    Paint backgroundPaint = new Paint();
    backgroundPaint.setColor(Color.parseColor("#FFEBEE")); // Light pinkish background
    canvas.drawRect(0, 0, pageInfo.getPageWidth(), pageInfo.getPageHeight(), backgroundPaint);

    // Set up paint for title text
    Paint titlePaint = new Paint();
    titlePaint.setColor(Color.parseColor("#D32F2F")); // Dark red for title
    titlePaint.setTextSize(32);
    titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
    titlePaint.setTextAlign(Paint.Align.CENTER);

    // Draw the title
    canvas.drawText("Wedding Invitation", pageInfo.getPageWidth() / 2, 60, titlePaint);

    // Set up paint for the invitation message
    Paint messagePaint = new Paint();
    messagePaint.setColor(Color.BLACK);
    messagePaint.setTextSize(18);
    messagePaint.setTextAlign(Paint.Align.LEFT);

    // Draw the bride and groom names in a special font style
    Paint coupleNamePaint = new Paint();
    coupleNamePaint.setColor(Color.parseColor("#1976D2")); // Blue color for names
    coupleNamePaint.setTextSize(24);
    coupleNamePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));

    // Draw the couple names
    canvas.drawText("Bride: " + brideName, 30, 100, coupleNamePaint);
    canvas.drawText("Groom: " + groomName, 30, 140, coupleNamePaint);

    // Set up the text for the message with word wrap and margins
    float marginLeft = 30;
    float marginRight = pageInfo.getPageWidth() - 30;
    float yPosition = 180; // Start below the couple's names

    // Split the invitation message into lines and wrap text
    String[] lines = invitationMessage.split("\n");
    for (String line : lines) {
        // Handle word wrapping if the text is too wide
        String[] words = line.split(" ");
        StringBuilder currentLine = new StringBuilder();
        for (String word : words) {
            float textWidth = messagePaint.measureText(currentLine + word + " ");
            if (textWidth < (marginRight - marginLeft)) {
                currentLine.append(word).append(" ");
            } else {
                canvas.drawText(currentLine.toString(), marginLeft, yPosition, messagePaint);
                yPosition += messagePaint.getTextSize() + 10; // Add spacing
                currentLine = new StringBuilder(word).append(" ");
            }
        }
        canvas.drawText(currentLine.toString(), marginLeft, yPosition, messagePaint);
        yPosition += messagePaint.getTextSize() + 10; // Add spacing between lines
    }

    Paint borderPaint = new Paint();
    borderPaint.setColor(Color.parseColor("#B71C1C")); // Dark red for border
    borderPaint.setStyle(Paint.Style.STROKE);
    borderPaint.setStrokeWidth(4);
    canvas.drawRect(20, 20, pageInfo.getPageWidth() - 20, pageInfo.getPageHeight() - 20, borderPaint);

    // Draw footer text with a different font and style
    Paint footerPaint = new Paint();
    footerPaint.setColor(Color.parseColor("#303F9F")); // Dark blue for footer text
    footerPaint.setTextSize(16);
    footerPaint.setTextAlign(Paint.Align.CENTER);
    canvas.drawText("Join us for a day full of love, laughter, and memories!", pageInfo.getPageWidth() / 2, pageInfo.getPageHeight() - 40, footerPaint);

    // Finish the page
    pdfDocument.finishPage(page);

    // Save the document to device storage
    String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
    File file = new File(directoryPath, "WeddingInvitation.pdf");

    try {
        pdfDocument.writeTo(new FileOutputStream(file));
        Toast.makeText(this, "PDF saved to Downloads folder!", Toast.LENGTH_LONG).show();
    } catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(this, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    // Close the document
    pdfDocument.close();
}

    // Method to display the invitation message and images
    private void displayInvitation(String invitationMessage) {
        // Hide all other UI elements
        guestNameEditText.setVisibility(View.GONE);
        brideImageView.setVisibility(View.VISIBLE);
        groomImageView.setVisibility(View.VISIBLE);
        findViewById(R.id.selectBrideImageButton).setVisibility(View.GONE);
        findViewById(R.id.selectGroomImageButton).setVisibility(View.GONE);
        findViewById(R.id.sendInvitationButton).setVisibility(View.GONE);

        // Set the invitation message
        invitationTextView.setText(invitationMessage);
        invitationTextView.setVisibility(View.VISIBLE);

        // Show the download PDF button
        Button downloadPdfButton = new Button(this);
        downloadPdfButton.setText("Download Invitation PDF");
        downloadPdfButton.setVisibility(View.VISIBLE);

        // Add the button to the layout
        LinearLayout layout = findViewById(R.id.invitationLayout); // Ensure 'invitationLayout' exists in your XML
        layout.addView(downloadPdfButton);

        // Set the download PDF button's click listener
        downloadPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf(invitationMessage);
            }
        });
    }
}
