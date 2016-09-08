package com.dam.kevoundfreunde.lab02;

import android.content.DialogInterface;
import android.icu.util.RangeValueIterator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener, AdapterView.OnItemClickListener {
    DecimalFormat f = new DecimalFormat("##.00");

    ElementoMenu[] listaBebidas;
    ElementoMenu[] listaPlatos;
    ElementoMenu[] listaPostre;

    private ListView listaSeleccion;
    private Button toggleReserva;
    private Spinner horario;
    private TextView pedido;
    private RadioGroup tipoPedido;
    private Button agregar;
    private Button confirmar;
    private Button reiniciar;
    private ElementoMenu seleccion = null;
    private float precioTotal = 0;
    private boolean confirmado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarListas();

        tipoPedido = (RadioGroup) findViewById(R.id.tipoPedido);
        pedido = (TextView) findViewById(R.id.listaPedido);
        horario = (Spinner) findViewById(R.id.spinnerHorario);
        toggleReserva = (Button) findViewById(R.id.toggleReserva);
        listaSeleccion = (ListView) findViewById(R.id.listView);

        agregar = (Button) findViewById(R.id.buttonAgregar);
        confirmar = (Button) findViewById(R.id.buttonConfirmar);
        reiniciar = (Button) findViewById(R.id.buttonReiniciar);

        tipoPedido.setOnCheckedChangeListener(this);
        listaSeleccion.setOnItemClickListener(this);

        agregar.setOnClickListener(this);
        confirmar.setOnClickListener(this);
        reiniciar.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ArrayAdapter<ElementoMenu> adaptador;
        ElementoMenu[] vacio = new ElementoMenu[0];
        adaptador = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, vacio);
        switch (checkedId){
            case -1:
                adaptador = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, vacio);
                break;
            case R.id.radioBebida:
                adaptador = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_single_choice, listaBebidas);
                break;
            case R.id.radioPlato:
                adaptador = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_single_choice, listaPlatos);
                break;
            case R.id.radioPostre:
                adaptador = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_single_choice, listaPostre);
                break;
        }
        listaSeleccion.setAdapter(adaptador);
    }

    @Override
    public void onClick(View boton) {
        switch(boton.getId()) {
            case R.id.buttonAgregar:
                if (seleccion != null && !confirmado) {
                    pedido.append(seleccion.toString() + '\n');
                    precioTotal += seleccion.getPrecio();
                    tipoPedido.clearCheck();
                    seleccion = null;
                } else if (seleccion == null) {
                    Log.v("hacerToast", "Toast no hay selección.");
                }
                else if(confirmado) {
                    Log.v("hacerToast", "Se confirmó ya.");
                }
                break;
            case R.id.buttonReiniciar:
                pedido.setText("");
                seleccion = null;
                tipoPedido.clearCheck();
                precioTotal = 0;
                confirmado = false;
                break;
            case R.id.buttonConfirmar:
                if(!confirmado && precioTotal != 0) {
                    pedido.append(String.format("\nTotal: $%.2f", precioTotal));
                    confirmado = true;
                }
                else if(confirmado) {
                    Log.v("hacerToast", "Toast de ya confirmado");
                }
                else {
                    Log.v("hacerToast", "Toast de pedí algo boludo");
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        seleccion = (ElementoMenu) listaSeleccion.getItemAtPosition(position);
    }

    class ElementoMenu {
        private Integer id;
        private String nombre;
        private Double precio;

        public ElementoMenu() {
        }

        public ElementoMenu(Integer i, String n, Double p) {
            this.setId(i);
            this.setNombre(n);
            this.setPrecio(p);
        }

        public ElementoMenu(Integer i, String n) {
            this(i,n,0.0);
            Random r = new Random();
            this.precio= (r.nextInt(3)+1)*((r.nextDouble()*100));
        }


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        @Override
        public String toString() {
            return this.nombre+ "( "+f.format(this.precio)+")";
        }
    }

    private void iniciarListas(){
        // inicia lista de bebidas
        listaBebidas = new ElementoMenu[7];
        listaBebidas[0]=new ElementoMenu(1,"Coca");
        listaBebidas[1]=new ElementoMenu(4,"Jugo");
        listaBebidas[2]=new ElementoMenu(6,"Agua");
        listaBebidas[3]=new ElementoMenu(8,"Soda");
        listaBebidas[4]=new ElementoMenu(9,"Fernet");
        listaBebidas[5]=new ElementoMenu(10,"Vino");
        listaBebidas[6]=new ElementoMenu(11,"Cerveza");
        // inicia lista de platos
        listaPlatos= new ElementoMenu[14];
        listaPlatos[0]=new ElementoMenu(1,"Ravioles");
        listaPlatos[1]=new ElementoMenu(2,"Gnocchi");
        listaPlatos[2]=new ElementoMenu(3,"Tallarines");
        listaPlatos[3]=new ElementoMenu(4,"Lomo");
        listaPlatos[4]=new ElementoMenu(5,"Entrecot");
        listaPlatos[5]=new ElementoMenu(6,"Pollo");
        listaPlatos[6]=new ElementoMenu(7,"Pechuga");
        listaPlatos[7]=new ElementoMenu(8,"Pizza");
        listaPlatos[8]=new ElementoMenu(9,"Empanadas");
        listaPlatos[9]=new ElementoMenu(10,"Milanesas");
        listaPlatos[10]=new ElementoMenu(11,"Picada 1");
        listaPlatos[11]=new ElementoMenu(12,"Picada 2");
        listaPlatos[12]=new ElementoMenu(13,"Hamburguesa");
        listaPlatos[13]=new ElementoMenu(14,"Calamares");
        // inicia lista de postres
        listaPostre= new ElementoMenu[15];
        listaPostre[0]=new ElementoMenu(1,"Helado");
        listaPostre[1]=new ElementoMenu(2,"Ensalada de Frutas");
        listaPostre[2]=new ElementoMenu(3,"Macedonia");
        listaPostre[3]=new ElementoMenu(4,"Brownie");
        listaPostre[4]=new ElementoMenu(5,"Cheescake");
        listaPostre[5]=new ElementoMenu(6,"Tiramisu");
        listaPostre[6]=new ElementoMenu(7,"Mousse");
        listaPostre[7]=new ElementoMenu(8,"Fondue");
        listaPostre[8]=new ElementoMenu(9,"Profiterol");
        listaPostre[9]=new ElementoMenu(10,"Selva Negra");
        listaPostre[10]=new ElementoMenu(11,"Lemon Pie");
        listaPostre[11]=new ElementoMenu(12,"KitKat");
        listaPostre[12]=new ElementoMenu(13,"IceCreamSandwich");
        listaPostre[13]=new ElementoMenu(14,"Frozen Yougurth");
        listaPostre[14]=new ElementoMenu(15,"Queso y Batata");

    }

}