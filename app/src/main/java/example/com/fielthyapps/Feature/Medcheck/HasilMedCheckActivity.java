package example.com.fielthyapps.Feature.Medcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import example.com.fielthyapps.Feature.History.HistoryActivity;
import example.com.fielthyapps.HomeActivity;
import example.com.fielthyapps.R;

public class HasilMedCheckActivity extends AppCompatActivity {
private TextView imt,lingkarperut,tekanandarah,guladarah,lemak,tV_indikator_tekanan,tV_indikator_gula,tV_indikator_imt,tV_kategori_ptm,tV_indikator_kolestrol;
private String status,date,uid,id,get_berat,get_tinggi,get_lingkar_perut,get_sistolik,get_diastolik,get_lemak,get_guladarah,get_bmi,get_gender;
private Button btn_selesai;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_med_check);
        imt = findViewById(R.id.tV_hasil_imt);
        lingkarperut = findViewById(R.id.tV_hasil_lingkar);
        tekanandarah = findViewById(R.id.tV_hasil_tekanan);
        guladarah = findViewById(R.id.tV_hasil_gula);
        tV_indikator_tekanan = findViewById(R.id.tV_indikator_tekanan);
        tV_indikator_gula = findViewById(R.id.tV_indikator_gula);
        tV_indikator_imt = findViewById(R.id.tV_indikator_imt);
//        tV_kategori_ptm = findViewById(R.id.tV_kategori_ptm);
        tV_indikator_kolestrol = findViewById(R.id.tV_indikator_kolestrol);
        btn_selesai = findViewById(R.id.btn_selesai);
        lemak = findViewById(R.id.tV_hasil_lemak);
        Intent iin = getIntent();
        final Bundle b = iin.getExtras();

        if (b != null) {
            id = (String) b.get("id");
            date = (String) b.get("date");
            uid = (String) b.get("uid");
            get_gender = (String) b.get("gender");
            get_berat = (String) b.get("berat");
            get_tinggi = (String) b.get("tinggi");
            get_lingkar_perut = (String) b.get("lingkarperut");
            get_sistolik = (String) b.get("sistolik");
            get_diastolik = (String) b.get("diastolik");
            get_guladarah = (String) b.get("guladarah");
            get_lemak = (String) b.get("lemak");
            get_bmi = (String) b.get("hasilbmi");
            status = (String) b.get("status");
            imt.setText(get_bmi);
            lingkarperut.setText(get_lingkar_perut);
            tekanandarah.setText(get_sistolik + "/" + get_diastolik);
            lemak.setText(get_lemak);
            guladarah.setText(get_guladarah);


            // Ganti koma dengan titik
            String valueWithDot = get_bmi.replace(',', '.');

            // Konversi string menjadi double
            double doubleValue = Double.parseDouble(valueWithDot);

            // Membulatkan double ke bilangan bulat terdekat
            int edu_score_imt = (int) Math.round(doubleValue);
//            float edu_score_imt = Float.parseFloat(get_bmi);


            int edu_lingkar = Integer.parseInt(get_lingkar_perut);
            int edu_sistolik = Integer.parseInt(get_sistolik);
            int edu_diastolik = Integer.parseInt(get_diastolik);
            int edu_gula = Integer.parseInt(get_guladarah);
            int edu_lemak = Integer.parseInt(get_lemak);
            String ket_imt, ket_darah, ket_gula, ket_lemak;
            if (edu_score_imt >= 25) {
                if (get_gender.equals("Laki - Laki")) {
                    if (edu_lingkar >= 90) {
                        ket_imt = "Obesitas";
                        tV_indikator_imt.setText("Anda Menderita Obesitas");
                    }else if (edu_lingkar <= 90) {
                        ket_imt = "Normal";
                        tV_indikator_imt.setText("Berat Badan Normal");

                    }
                } else if (get_gender.equals("Perempuan")) {
                    if (edu_lingkar >= 80) {
                        ket_imt = "Obesitas";
                        tV_indikator_imt.setText("Anda Menderita Obesitas");

                    } else if (edu_lingkar <= 80) {
                        ket_imt = "Normal";
                        tV_indikator_imt.setText("Berat Badan Normal");

                    }
                }
            } else if (edu_score_imt >= 23 && edu_score_imt <= 24.9) {
                ket_imt = "Kelebihan BB";
                tV_indikator_imt.setText("Anda Kelebihan Berat Badan");
            } else {
                if (get_gender.equals("Laki - Laki")) {
                    if (edu_lingkar <= 90) {
                        ket_imt = "Normal";
                        tV_indikator_imt.setText("Berat Badan Normal");

                    }else if (edu_lingkar >= 90) {
                        ket_imt = "Obesitas";
                        tV_indikator_imt.setText("Anda Menderita Obesitas");


                    }
                } else if (get_gender.equals("Perempuan")) {
                    if (edu_lingkar >= 80) {
                        ket_imt = "Obesitas";
                        tV_indikator_imt.setText("Anda Menderita Obesitas");

                    }else if (edu_lingkar <= 80) {
                        ket_imt = "Normal";
                        tV_indikator_imt.setText("Berat Badan Normal");

                    }
                }
            }

            

            if (edu_sistolik < 130 && edu_diastolik <= 80) {
                ket_darah = "Normal";
                tV_indikator_tekanan.setText("Tekanan Darah Anda Normal");
                
            } else if (edu_sistolik <= 139 &&  edu_diastolik <= 89) {
                ket_darah = "Berisiko";
                tV_indikator_tekanan.setText("Tekanan Darah Anda Beresiko Hipertensi");
            } else if (edu_sistolik >= 140 && edu_diastolik >= 90) {
                ket_darah = "Hipertensi";
                tV_indikator_tekanan.setText("Anda Menderita Hipertensi");
            }

            if (edu_gula <= 200) {
                ket_gula = "Normal";
                tV_indikator_gula.setText("Gula Darah Anda Normal");
            } else if (edu_gula >= 200) {
                ket_gula = "Berisiko Diabetes";
                tV_indikator_gula.setText("Gula Darah Anda Berisiko Diabetes");
            }

            if (edu_lemak <= 200) {
                ket_lemak = "Normal";
                tV_indikator_kolestrol.setText("Kolestrol Anda Normal");
            } else if (edu_lemak > 200) {
                ket_lemak = "Dislipidemia";
                tV_indikator_kolestrol.setText("Anda Menderita Dislipidemia");
            }

        }

        btn_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("testmedcheck")){
                    startActivity(new Intent(HasilMedCheckActivity.this, HomeActivity.class));
                    finish();
                }else if (status.equals("historymedcheck")){
                    startActivity(new Intent(HasilMedCheckActivity.this, HistoryActivity.class));
                    finish();
                }



            }
        });


    }
}