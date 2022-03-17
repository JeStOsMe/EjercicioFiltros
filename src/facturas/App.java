package facturas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

// clase factura
class Factura{
    String descripcion;
    int importe;
    double codigo;
    int cantidad;
    Date fecha;
    
    Factura(String descripcion,int importe, int cantidad, String fecha){
        this.descripcion=descripcion;
        this.importe=importe;
        this.cantidad = cantidad;
        try { //Si la fecha ingresada no cumple el formato, se toma la fecha actual
            this.fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (ParseException e) {
            this.fecha = Calendar.getInstance().getTime();
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Date getFecha() {
        return fecha;
    }
    
    int getImporte(){
        return importe;
    }

    double getCodigo(){
        return codigo;
    }
    /*Recibe la lista de facturas. Filtra: si no existe una factura con el código generado, entonces
    le asigna a la factura que invocó el método el código generado. En caso contrario, se vuelve
    a llamar la función de forma recursiva, hasta generar un código aleatorio satisfactorio.*/
    void generarCodigo(List<Factura> lista){
        Optional<Factura> aux;    
        double codigoAux = Math.round(Math.random()*100);

        aux = lista.stream().
                    filter(factura -> factura.getCodigo() == codigoAux).
                    findFirst();
        if (aux.isEmpty()){
            this.codigo = codigoAux;
        } else{
            this.generarCodigo(lista);
        }
    }
}

public class App {

    public static Scanner sc = new Scanner(System.in);

    public static void filtros(List<Factura> lista){
        
        boolean enBucle = true;

        do{

            List<Factura> encontrados = new ArrayList<Factura>();
            Date fecha;
            int opcion, cantidad;
            double codigo;

            System.out.println("\n1. Filtrar por fecha");
            System.out.println("2. Filtrar por cantidades");    
            System.out.println("3. Filtrar por código de factura.");
            System.out.println("4. Salir");
            System.out.print("Opción --> ");

            try{
                opcion = Integer.parseInt(sc.nextLine());
            } catch (Exception ex){
                System.out.println("ERROR: solo se admiten números. Reiniciando filtros...");
                continue;
            }

            switch(opcion){
                case 1: //PRINCIPAL 1
                    System.out.println("\n1. Consultar por fechas iguales: ");
                    System.out.println("2. Consultar hasta fecha específica: ");
                    System.out.println("3. Consultar después de fecha específica: ");
                    System.out.print("Opción --> ");
                    try{
                        opcion = Integer.parseInt(sc.nextLine());
                    } catch (Exception ex){
                        System.out.println("ERROR: solo se admiten números. Reiniciando filtros...");
                        continue;
                    }
                    switch(opcion){
                        case 1:
                            System.out.print("Ingrese la fecha a consultar en formato dd/MM/yyyy: ");
                            try {
                                fecha = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
                            } catch (ParseException e) {
                                System.out.println("ERROR: formato mal ingresado. Obteniendo fecha actual");
                                fecha = Calendar.getInstance().getTime();
                            }

                            for (Factura factura: lista){
                                if (factura.getFecha().equals(fecha)){
                                    encontrados.add(factura);
                                }
                            }

                            imprimirFacturas(encontrados);

                            break;
                        
                        case 2:
                            System.out.print("Ingrese la fecha a consultar en formato dd/MM/yyyy: ");
                            try {
                                fecha = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
                            } catch (ParseException e) {
                                System.out.println("ERROR: formato mal ingresado. Obteniendo fecha actual");
                                fecha = Calendar.getInstance().getTime();
                            }
                            
                            for (Factura factura: lista){
                                if (factura.getFecha().before(fecha)){
                                    encontrados.add(factura);
                                }
                            }

                            imprimirFacturas(encontrados);

                            break;
                        
                        case 3:
                            System.out.println("Ingrese la fecha a consultar en formato dd/MM/yyyy: ");
                            try {
                                fecha = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
                            } catch (ParseException e) {
                                System.out.println("ERROR: formato mal ingresado. Obteniendo fecha actual");
                                fecha = Calendar.getInstance().getTime();
                            }
                            
                            for (Factura factura: lista){
                                if (factura.getFecha().after(fecha)){
                                    encontrados.add(factura);
                                }
                            }

                            imprimirFacturas(encontrados);

                            break;
                        
                        default:
                            System.out.println("\nERROR: opción no permitida...");
                            return;

                    }

                    break;
                
                case 2: //PRINCIPAL 2
                    System.out.println("\n1. Consultar por cantidades iguales: ");
                    System.out.println("2. Consultar cantidades menores: ");
                    System.out.println("3. Consultar cantidades mayores: ");
                    System.out.print("---> ");
                    try {
                        opcion = Integer.parseInt(sc.nextLine());
                    } catch (Exception ex) {
                        System.out.println("ERROR: solo se admiten números. Reiniciando filtros...");
                        continue;
                    }
                    switch (opcion) {
                        case 1:
                            System.out.print("\nIngrese la cantidad a consultar: ");
                            try {
                                cantidad = Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                System.out.println("ERROR: solo se admiten números. Saliendo...");
                                return;
                            }

                            for (Factura factura : lista) {
                                if (factura.getCantidad() == cantidad) {
                                    encontrados.add(factura);
                                }
                            }

                            imprimirFacturas(encontrados);

                            break;

                        case 2:
                            System.out.print("\nIngrese la cantidad límite superior: ");
                            try {
                                cantidad = Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                System.out.println("ERROR: solo se admiten números. Saliendo...");
                                return;
                            }

                            for (Factura factura : lista) {
                                if (factura.getCantidad() < cantidad) {
                                    encontrados.add(factura);
                                }
                            }

                            imprimirFacturas(encontrados);

                            break;

                        case 3:
                        System.out.print("\nIngrese la cantidad límite inferior: ");
                        try {
                            cantidad = Integer.parseInt(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("ERROR: solo se admiten números. Saliendo...");
                            return;
                        }

                        for (Factura factura : lista) {
                            if (factura.getCantidad() > cantidad) {
                                encontrados.add(factura);
                            }
                        }

                        imprimirFacturas(encontrados);

                        break;

                        default:
                            System.out.println("\nERROR: opción no permitida...");
                            return;

                    }

                    break;
                
                case 3:
                    System.out.print("Ingrese el código a buscar: ");
                    try{
                        codigo = Double.parseDouble(sc.nextLine());
                    } catch (Exception ex){
                        System.out.println("ERROR: solo se admiten números. Saliendo...");
                        return;
                    }

                    for (Factura factura: lista){
                        if (factura.getCodigo() == codigo){
                            encontrados.add(factura);
                        }
                    }

                    imprimirFacturas(encontrados);

                    break;
                
                case 4:
                    enBucle = false;
                    return;

                default:
                    System.out.println("ERROR: opción no permitida. Saliendo...");
                    return;
            }

        } while (enBucle);
    }

    public static Factura crearFactura(){
        Factura aux;
        String descripcion, fecha;
        int importe, cantidad;

        try{
            System.out.print("Por favor, ingrese la descripción del producto: ");
            descripcion = sc.nextLine();
            System.out.print("\nIngrese el valor del producto (solo enteros): ");
            importe = Integer.parseInt(sc.nextLine());
            System.out.print("\nAhora, la cantidad de este producto: " );
            cantidad = Integer.parseInt(sc.nextLine());
            System.out.print("\nFinalmente, ingrese la fecha a registrar en formato dd/MM/yyyy: ");
            fecha = sc.nextLine();

            aux = new Factura(descripcion, importe, cantidad, fecha);
            return aux;

        } catch(Exception ex){
            System.out.println("Algo salió mal. Intente de nuevo.");
            return null;
        }
    }

    public static void imprimirFacturas(List<Factura> lista){
        lista.forEach(factura -> {
            System.out.println("Descripcion: " + factura.getDescripcion()
                            + "\n\tValor unitario: " + factura.getImporte()
                            + "\n\tCantidad: " + factura.getCantidad()
                            + "\n\tTotal: " + (factura.getImporte() * factura.getCantidad())
                            + "\n\tFecha factura: " + factura.getFecha()
                            + "\n\tCódigo: " + factura.getCodigo());
        }); 
    }

   
    public static void main(String[] args) {
        // generar una lista
        List<Factura> lista=new ArrayList<Factura>();
        
        boolean salida = false;
        int opcion;
        Factura aux;

        do {

            System.out.println("\n\n     ***** Algoritmo registra facturas *****");
            System.out.println("1. Crear factura");
            System.out.println("2. Listar todas las facturas");
            System.out.println("3. Filtrar");
            System.out.println("4. Salir del algoritmo");
            System.out.print("Opción --> ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (Exception ex) {
                System.out.println("\nERROR: solo se admiten números. Reiniciando...");
                continue;
            }

            switch(opcion){
                case 1:
                    aux = crearFactura();
                    if (aux == null){
                        continue;
                    }
                    aux.generarCodigo(lista);
                    lista.add(aux);
                    break;
                case 2:
                    imprimirFacturas(lista);
                    break;
                case 3:
                    filtros(lista);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("\nERROR: opción no válida. Entrando al ciclo...");
                    continue;

            }

            
            
            System.out.println("Salir del ciclo? Si = 0 ----- NO = 1");
            System.out.print("Opción --> ");
            try{
                opcion = Integer.parseInt(sc.nextLine());
            } catch(Exception ex){
                System.out.println("ERROR: solo se admiten números. Reiniciando...");
                continue;
            }
            switch (opcion) {
                case 1:
                    System.out.println("\nEntrando al ciclo");
                    break;
                case 0:
                    System.out.println("\nSaliendo del ciclo...");
                    salida = true;
                    break;
                default:
                    System.out.println("\nERROR: opción no válida. Entrando al ciclo...");
            }
        } while (salida == false);

    }
    
}