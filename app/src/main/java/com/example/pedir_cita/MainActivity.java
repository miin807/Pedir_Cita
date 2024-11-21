package com.example.pedir_cita;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText EditDni, Editfecha, Edithora;
    private Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditDni = findViewById(R.id.dni);
        Editfecha = findViewById(R.id.dia);
        Edithora = findViewById(R.id.hora);
        confirmar = findViewById(R.id.enviar);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCita();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void validarCita() {
        String dni = EditDni.getText().toString().trim();
        String dia = Editfecha.getText().toString().trim();
        String hora = Edithora.getText().toString().trim();


        if (!validarDni(dni)) {
            mostrarMensaje("DNI inválido. Debe contener 8 números y una letra final.");
            return;
        }

        if (!validarDia(dia)) {
            mostrarMensaje("El día debe ser entre lunes y viernes.");
            return;
        }

        if (!validarHora(hora)) {
            mostrarMensaje("La hora debe estar entre las 9:00 y las 14:00.");
            return;
        }

        mostrarMensaje("Cita validada con éxito.");

    }

    private boolean validarDni(String dni) {
        return dni.matches("\\d{8}[A-Za-z]");//tiene que ser de 8 numeros y una letra
    }
    private boolean validarDia(String dia) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date fecha = dateFormat.parse(dia);
            if (fecha != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
                return diaSemana >= Calendar.MONDAY && diaSemana <= Calendar.FRIDAY;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false; // Retorna falso si hay error o fecha inválida
    }

    private boolean validarHora(String hora) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date horaCita = timeFormat.parse(hora);
            if (horaCita != null) {
                //Compruebo en rango de horas
                Date horaInicio = timeFormat.parse("09:00");
                Date horaFin = timeFormat.parse("14:00");
                return horaCita.equals(horaInicio) || horaCita.equals(horaFin) ||
                        (horaCita.after(horaInicio) && horaCita.before(horaFin));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
