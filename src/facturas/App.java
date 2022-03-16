package facturas;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

// clase factura
class Factura{
    String descripcion;
    int importe;
    
    Factura(String descripcion,int importe){
        this.descripcion=descripcion;
        this.importe=importe;
    }
    
    int getImporte(){
        return importe;
    }
}

public class App {

   
    public static void main(String[] args) {
        // registros de la data
        Factura f=new Factura("ordenador",1000);
        Factura f2=new Factura("movil",300);
        Factura f3=new Factura("imporesora",200);
        Factura f4=new Factura("imac",1500);
        
        // generar una lista
        List<Factura> lista=new ArrayList<Factura>();
        
        // agregar los productos de la factura
        lista.add(f);
        lista.add(f2);
        lista.add(f3);
        lista.add(f4);
        
        Predicate<Factura> predicado= new Predicate<Factura>() {
            @Override
            public boolean test(Factura t) {
                return t.getImporte()>300;
            }
        };
        
        Factura facturaFiltro=lista.stream()
                .filter(predicado).findFirst().get();
        
        System.out.println("FACTURA UNICA : "+facturaFiltro.getImporte());
        
        
        /*
        // filtraje funcional con streams
        Factura facturaFiltro=lista.stream()
                .filter(elemento->elemento.getImporte()>300)
                .findFirst()
                .get();
        System.out.println(facturaFiltro.getImporte());
        */
    }
    
}