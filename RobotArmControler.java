

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.glu.GLU;
import org.lwjgl.devil.IL;
import org.lwjgl.input.Keyboard;
import java.io.*;
import org.lwjgl.devil.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

import java.lang.*;
import java.util.*;

import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;




import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.w3c.dom.*;



import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.text.NumberFormat;
import java.io.*;

import javax.comm.*;




/*Versión 1.2.1
 * */

/**
 * @author jsoto
 * Programa para controlar un brazo robotico virtual en tres dimensiones
 */
public class RobotArmControler{
	

	
	ByteBuffer bb = ByteBuffer.allocateDirect(16);
    
	
	private int btnRotarY[] = new int[3];
	private int btnMoverY[] = new int[3];
	private int btnAcercar[] = new int[3];
	private int btnServo0[] = new int[3];
	private int btnServo1[] = new int[3];
	private int btnServo2[] = new int[3];
	private int btnServo3[] = new int[3];
	private int btnServo4[] = new int[3];
	private int btnTomarObjeto[] = new int[3];
	private int btnTransmisionSerial[] = new int[3];
	private int btnSiguienteCuadro[] = new int[3];
	private int btnLiberarObjeto[] = new int[3];
	private int btnGuardarXml[] = new int[3];
	private int btnEjecutarMovimiento[] = new int[3];
	private int btnCuadro1[] = new int[3];
	private int btnGuardarClave[] = new int[3];
	private int btnCopiarClave[] = new int[3];
	private int btnPegarClave[] = new int[3];
	
	/**
	 * Identificador de la clave copiada, inicialmente la clave copiada es la clave 1
	 */
	private int idClaveCopiada=0;
	
	boolean copiarClave = false;
	boolean pegarClave = false;
	
	private boolean done = false;
	/**
	 * Determina si se ejecuta en pantalla completa o en ventana
	 */
    private boolean fullscreen = false;
    /**
     * Título de la ventana
     */
    private String windowTitle = "Controlador de brazos robóticos 3D";
    private boolean f1 = false;

    private float rtri;
    private float rquad;
    private DisplayMode displayMode;

    private int cuadricula;
    private int[]  modelo = new int[200];
	/**
	 * HashMaps donde se guardará la información del modelo que se lee del archivo Obj
	 */
	private Map mapMaterial = new HashMap();
	/**
	 * HashMaps donde se guardará la información de las caras del modelo
	 */
	private Map mapCara = new HashMap();
	/**
	 * HashMaps donde se guardará la información de los vértices del modelo
	 */
	private Map mapVertice = new HashMap();
	/**
	 * HashMaps donde se guardará la información de los grupos del modelo
	 */
	private Map mapGrupo = new HashMap();

	/**
	 *Incremento para la rotación del brazo
	 */
	private float offsetRotacionBrazo = 1;
	/**
	 *Incremento para la rotación del ante brazo
	 */
	private float offsetRotacionAnteBrazo = 1;
	/**
	 *Incremento para la rotación de la base
	 */
	private float offsetRotacionBaseHexagonal = 1;
	/**
	 *Incremento para la rotación de la mano
	 */
	private float offsetRotacionMano = 1;
	/**
	 *Incremento para la rotación del dedo 1
	 */
	private float offsetRotacionDedo1 = 1;
	/**
	 *Incremento para la rotación del dedo 2
	 */
	private float offsetRotacionDedo2 = 1;

	/**
	 * Ancho de la cuadrúcula
	 */
    private float anchoCuadricula = 80;
    /**
     * Tamaño de cada uno de los cuadros de la cuadrícula
     */
    private float divCuadricula = 5;

	/**
	 * Archivo Obj generado con Maya 6.5
	 */
	private String fileObj = "Robot.obj";
	/**
	 * Archivo de materiales
	 */
	private String fileMaterial = "";
	/**
	 * Angulo inicial del brazo
	 */
	float angleBrazo = 0;
	/**
	 * Angulo inicial del ante brazo
	 */
	float angleAnteBrazo = 0;
	/**
	 * Angulo inicial de la base
	 */
	float angleBaseHexagonal = 0;
	/**
	 * Angulo inicial de la mano
	 */
	float angleMano = 0;
	/**
	 * Angulo inicial del dedo 1
	 */
	float angleDedo1 = 0;
	/**
	 * Angulo inicial del dedo 2
	 */
	float angleDedo2 = 0;
	/**
	 * Rotación máxima del brazo
	 */
	float rotacionMaximaBrazo=180;
	/**
	 * Rotación máxima del ante brazo
	 */
	float rotacionMaximaAnteBrazo=180;
	/**
	 * Rotación máxima de la base
	 */
	float rotacionMaximaBaseHexagonal=45;
	/**
	 * Rotación máxima de la mano
	 */
	float rotacionMaximaMano=180;
	/**
	 * Rotación máxima del dedo 1
	 */
	float rotacionMaximaDedo1=180;
	/**
	 * Rotación máxima del dedo 2
	 */
	float rotacionMaximaDedo2=180;




	/**
	 * Posición inicial en eje X del centro de rotación del brazo
	 */
	float pivotBrazoX=0;
	/**
	 * Posición inicial en eje Y del centro de rotación del brazo
	 */
	float pivotBrazoY=0;
	/**
	 * Posición inicial en eje Z del centro de rotación del brazo
	 */
	float pivotBrazoZ=0;

	/**
	 * Posición inicial en eje X del centro de rotación del ante brazo
	 */
	float pivotAnteBrazoX=0;
	/**
	 * Posición inicial en eje Y del centro de rotación del ante brazo
	 */
	float pivotAnteBrazoY=0;
	/**
	 * Posición inicial en eje Z del centro de rotación del ante brazo
	 */
	float pivotAnteBrazoZ=0;

	/**
	 * Posición inicial en eje X del centro de rotación de la base
	 */
	float pivotBaseHexagonalX=0;
	/**
	 * Posición inicial en eje Y del centro de rotación de la base
	 */
	float pivotBaseHexagonalY=0;
	/**
	 * Posición inicial en eje Z del centro de rotación de la base
	 */
	float pivotBaseHexagonalZ=0;

	/**
	 * Posición inicial en eje X del centro de rotación de la mano
	 */
	float pivotManoX=0;
	/**
	 * Posición inicial en eje Y del centro de rotación de la mano
	 */
	float pivotManoY=0;
	/**
	 * Posición inicial en eje Z del centro de rotación de la mano
	 */
	float pivotManoZ=0;

	/**
	 * Posición inicial en eje X del centro de rotación de la dedo 1
	 */
	float pivotDedo1X=0;
	/**
	 * Posición inicial en eje Y del centro de rotación de la dedo 1
	 */
	float pivotDedo1Y=0;
	/**
	 * Posición inicial en eje Z del centro de rotación de la dedo 1
	 */
	float pivotDedo1Z=0;

	/**
	 * Posición inicial en eje X del centro de rotación de la dedo 2
	 */
	float pivotDedo2X=0;
	/**
	 * Posición inicial en eje Y del centro de rotación de la dedo 2
	 */
	float pivotDedo2Y=0;
	/**
	 * Posición inicial en eje Z del centro de rotación de la dedo 2
	 */
	float pivotDedo2Z=0;

	/**
	 * Posición inicial en X de la cámara
	 */
	float posCamaraX = 0;
	/**
	 * Posición inicial en Y de la cámara
	 */
	float posCamaraY = 5;
	/**
	 * Posición inicial en Z de la cámara
	 */
	float posCamaraZ = 0;
	/**
	 * Posición inicial en X del objetivo de la cámara
	 */
	float posObjetivoX=0;
	/**
	 * Posición inicial en Y del objetivo de la cámara
	 */
	float posObjetivoY=5;
	/**
	 * Posición inicial en Z del objetivo de la cámara
	 */
	float posObjetivoZ=0;

	/**
	 * Angulo inicial en el plano XZ
	 */
	float angleCamaraXZ = 0;
	/**
	 * Angulo inicial en el plano XY
	 */
	float angleCamaraXY = 0;
	/**
	 * Distancia entre la cámara y el objetivo
	 */
	float distanciaCamaraObjetivo = 30;
	/**
	 * Distancia inicial entre la cámara y la información que se
	 * muestra en la pantalla
	 */
	float distanciaCamaraInfo = 10;
	/**
	 * Incremento para el ángulo de la cámara
	 */
	float offsetAnguloCamara = 1;

	/**
	 * Inicialización del DOM para leer el archivo XML de configuracion
	 */
	String archivoConfiguracion = "configuracion.xml";
	DocumentBuilderFactory docConfigBuilderFactory;
	DocumentBuilder docConfigBuilder;
	Document docConfig;

	/**
	 * Inicialización del DOM para leer el archivo XML de animacion
	 */
	String archivoAnimacion = "movimiento001.xml";
	DocumentBuilderFactory docAnimacionBuilderFactory;
	DocumentBuilder docAnimacionBuilder;
	Document docAnimacion;

	

	private int base;
	private static final Color OPAQUE_WHITE = new Color(0xFFFFFFFF, true);
    private static final Color TRANSPARENT_BLACK = new Color(0x00000000, true);
    /**
     * Ancho de la pantalla
     */
    int anchoPantalla = 800;
    /**
     * Alto de la pantalla
     * */
    int altoPantalla = 600;

    float distCamaraInfo = 5;

    /**
     * Indica si se ejecuta la función mover la cámara sobre eje Y
     */
    boolean moverSobreEjeY = false;
    /**
     * Indica si se ejecuta la función rotar la cámara sobre eje Y
     */
    boolean rotarSobreEjeY = false;
    /**
     * Indica si se ejecuta la función de zoom
     */
    boolean zoom = false;
    /**
     * Indica si se ejecuta la función rotar servo 0
     */
    boolean rotarServo0 = false;
    /**
     * Indica si se ejecuta la función rotar servo 1
     */
    boolean rotarServo1 = false;
    /**
     * Indica si se ejecuta la función rotar servo 2
     */
    boolean rotarServo2 = false;
    /**
     * Indica si se ejecuta la función rotar servo 3
     */
    boolean rotarServo3 = false;
    /**
     * Indica si se ejecuta la función rotar servo 4
     */
    boolean rotarServo4 = false;
    /**
     * Indica si se ejecuta la función pararse en el primer cuadro
     */
    boolean irPrimerCuadro = false;
    /**
     * Indica si se ejecuta la función pararse en el siguiente cuadro
     */
    boolean irSiguienteCuadro = false;
    /**
     * Indica si se ejecuta la función transmitir información por el puerto
     */
    boolean transmitirPuerto = false;
    /**
     * Indica si se ejecuta la función ejecutar animación
     */
    boolean ejecutarAnimacion = false;
    /**
     * Indica si se ejecuta la función guardar clave
     */
    boolean guardarClave = false;
    /**
     * Indica si se ejecuta la función tomar objeto
     */
    boolean tomarObjeto = false;
    /**
     * Indica si se ejecuta la función soltar objeto
     */
    boolean soltarObjeto = false;
    /**
     * Indica si se ejecuta la función guardar movimiento en archivo XML
     */
    boolean guardarMovimientoXml = false;
    

	/**
	 * Claves de la animacion
	 */
	private Map mapClaves = new HashMap();
	/**
	 * Numero de claves de una animacion
	 */
	private int numClaves = 0;
	private Clave claveActual;
	/**
	 * Claves para la animacion
	 */
	Clave cPrev;
	Clave cNext;

	
	/**
	 * Indica si el servo 0 se esta movimendo
	 */
	boolean isMovingServo0 = false;
	/**
	 * Indica si el servo 1 se esta movimendo
	 */
	boolean isMovingServo1 = false;
	/**
	 * Indica si el servo 2 se esta movimendo
	 */
	boolean isMovingServo2 = false;
	/**
	 * Indica si el servo 3 se esta movimendo
	 */
	boolean isMovingServo3 = false;
	/**
	 * Indica si el servo 4 se esta movimendo
	 */
	boolean isMovingServo4 = false;
	
	
	/**
	 * Indica si el objeto esta tomado o no
	 */
	int objetoTomado = 0;
	/**
	 * Posición X del objeto
	 */
	float objetoPosX=0;
	/**
	 * Posición Y del objeto
	 */
	float objetoPosY=0;
	/**
	 * Posición Z del objeto
	 */
	float objetoPosZ=0;
	
	/**
	 * Puerto serial por el cual se transmitira la información 
	 */
	String idPuerto;
	
	/**
	 * Velocidad de transmición por el puerto serial
	 */
	
	int baudRate;
	
	int irSigClaveNum = 0;

	/**
	 * Función principal del programa
	 * @param args
	 */
	
	

    public static void main(String args[]) {
        boolean fullscreen = false;
        if(args.length>0) {
            if(args[0].equalsIgnoreCase("fullscreen")) {
                fullscreen = true;
            }
        }

        RobotArmControler b3D = new RobotArmControler();
        b3D.run(fullscreen);
    }
    /**
     * Función principal o loop infinito que llama a OpenGL
     * @param fullscreen
     */
    public void run(boolean fullscreen) {
        this.fullscreen = fullscreen;
        try {
            init();
            while (!done) {
                mainloop();
                render();
                Display.update();
            }
            cleanup();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    /**
     * En esta metodo se ejecuta el loop infinito, y donde se capturan
     * los eventos de teclas y otros.
     * El procedimeiento Keyboar.isKeyDown() determina cual es la tecla
     * que se tiene presionada en el momneto.
     * */
    private void mainloop() throws FileNotFoundException, IOException {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
            done = true;
        }
        
        /**
         * Copiar la posición de los servos en la clave actual
         */
        if(Keyboard.isKeyDown(Keyboard.KEY_C)){
        	this.copiarClave = true;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    guardarClave = false; 
    	    this.soltarObjeto = false;
    	    this.tomarObjeto = false;        	
        	this.copiarClaveActual();
        }
        /**
         * Asignar los movimientos copiados a la clave actual
         */
        if(Keyboard.isKeyDown(Keyboard.KEY_V)){
        	this.pegarClave = true;
        	this.copiarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    guardarClave = false; 
    	    this.soltarObjeto = false;
    	    this.tomarObjeto = false;        	
        	this.pegarClaveActual();
        }
        /**
         * Guardar el movimiento en el archvo XML
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_F)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = true;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    guardarClave = false; 
    	    this.soltarObjeto = false;
    	    this.tomarObjeto = false;
        	
    	    
   	    	this.guardarMovimientoXml();

    	    
        }
        
        
        
        /**
         * Tomar el objeto
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_O)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    guardarClave = false; 
    	    this.soltarObjeto = false;
    	    this.tomarObjeto = true;
        	if(this.claveActual.objetoTomado==0)
        		this.claveActual.objetoTomado=1;
        }
        /**
         * Liberar el objeto
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_L)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    guardarClave = false; 
    	    this.soltarObjeto = true;
    	    this.tomarObjeto = false;
        	if(this.claveActual.objetoTomado==1)   		
        		this.claveActual.objetoTomado=0;
        }
        /**
         * Guardar el cuadro la infroamación del cuadro clave
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_G)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    guardarClave = true;
    	    
    	    if(guardarClave){
    	    	this.guardarValoresClave();
    	    	guardarClave =false;


    	    }
        }
        
        /**
         * Ejecutar el movimiento en forma virtual
         * */

        if(Keyboard.isKeyDown(Keyboard.KEY_P)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = true;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }
        if(ejecutarAnimacion){

        		this.ejecutarMovimiento();
        }

        /**
         * Transmitir el movimeiento por el puerto serial 
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_T)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = true;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    this.transmitirMovimientos();
        }
        /**
         * Parars en el primer cuadro clave
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_N)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    this.pararseEnClave(1);
        }
        /**
         * Parase en la siguiente clave
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = true;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
    	    
    	    if (irSiguienteCuadro)
    	    {
    	    	
    	    	irSiguienteCuadro=false;
    	    	
    	    	
    	    	
    	    	
    	    	if (this.irSigClaveNum==20)
    	    		this.irSigClaveNum =0;
    	    	
    	    	if (this.irSigClaveNum==0)
    	    		this.pararseEnClave(this.claveActual.siguienteClave);
    	    	this.irSigClaveNum++;
    	    }
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
        }
        /**
         * Mover la cámara en el eje Y
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_Y)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = true;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }
        /**
         * Rotar la camara al rededor del brazo
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_R)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = true;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }
        /**
         * Hacer zoom hacia el brazo robótico
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = true;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }
        /**
         * Activar el servo 0 para moverlo
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_0)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = true;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }
        /**
         * Activar el servo 1 para moverlo
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_1)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = true;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }
        /**
         * Activar el servo 2 para moverlo
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_2)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = true;
    	    rotarServo3 = false;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }
        /**
         * Activar el servo 3 para moverlo
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_3)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = true;
    	    rotarServo4 = false;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }
        /**
         * Activar el servo 4 para moverlo
         * */
        if(Keyboard.isKeyDown(Keyboard.KEY_4)){
        	this.copiarClave = false;
        	this.pegarClave = false;
        	this.guardarMovimientoXml = false;
        	moverSobreEjeY = false;
    	    rotarSobreEjeY = false;
    	    zoom = false;
    	    rotarServo0 = false;
    	    rotarServo1 = false;
    	    rotarServo2 = false;
    	    rotarServo3 = false;
    	    rotarServo4 = true;
    	    irPrimerCuadro = false;
    	    irSiguienteCuadro = false;
    	    transmitirPuerto = false;
    	    ejecutarAnimacion = false;
    	    guardarClave = false;
    	    this.soltarObjeto=false;
    	    this.tomarObjeto=false;
        }

        if(Display.isCloseRequested()) {                     // Salir si se cierra la ventana
            done = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F1) && !f1) {    // Detemina si F1 se esta presionando
            f1 = true;                                      // Le indica al programa que F1 esta sostenido
            switchMode();                                   // Cambiar entre full screen y modo de ventana
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_F1)) {          // Determina si F1 se esta presionando
            f1 = false;
        }



        /**
         * Rotar la base hexagonal
         * */
        if(this.rotarServo0&&Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {

        	angleBaseHexagonal+=offsetRotacionBaseHexagonal;
        }
        if(this.rotarServo0&&Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {

        	angleBaseHexagonal-=offsetRotacionBaseHexagonal;

        }

        /**
         * Rotar el antebrzo
         * */

        if(this.rotarServo1&&Keyboard.isKeyDown(Keyboard.KEY_DOWN)){

			angleAnteBrazo+=offsetRotacionAnteBrazo;
        }

        if(this.rotarServo1&&Keyboard.isKeyDown(Keyboard.KEY_UP)){

			angleAnteBrazo-=offsetRotacionAnteBrazo;
        }

        /**
         * Rotar el brazo
         */

        if(this.rotarServo2&&Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			angleBrazo+=offsetRotacionBrazo;
        }

        if(this.rotarServo2&&Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			angleBrazo-=offsetRotacionBrazo;
        }

        /**
         * Rotar la mano
         */

        if(this.rotarServo3&&Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			angleMano+=offsetRotacionMano;
        }

        if(this.rotarServo3&&Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			angleMano-=offsetRotacionMano;
        }

        /**
         * Abrir y cerrar los dedos
         */

        if(this.rotarServo4&&Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			angleDedo1+=offsetRotacionDedo1;
			angleDedo2-=offsetRotacionDedo2;
        }

        if(this.rotarServo4&&Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
        	angleDedo1-=offsetRotacionDedo1;
			angleDedo2+=offsetRotacionDedo2;
        }

        /**
         * Rotar la camara sobre el ejey
         */
        if(this.rotarSobreEjeY && Keyboard.isKeyDown(Keyboard.KEY_LEFT) ) {

			this.angleCamaraXZ+=this.offsetAnguloCamara;

        }

        if(this.rotarSobreEjeY && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){

        	this.angleCamaraXZ-=this.offsetAnguloCamara;
        	
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_F)){
        	
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
        	
        }


        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
        	if(this.moverSobreEjeY==true){
        		this.posCamaraY-=0.2;
        		this.posObjetivoY-=0.2;
        	}

        	if(this.zoom==true){
        		this.distanciaCamaraObjetivo-=0.25f;
        	}
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
        	if(this.moverSobreEjeY==true){
        		this.posCamaraY+=0.2;
        		this.posObjetivoY+=0.2;
        	}

        	if(this.zoom==true){
        		this.distanciaCamaraObjetivo+=0.25f;
        	}
        }

    }

    /**
     * Cambia el modo de pantalla de pantalla completa a ventana
     *
     */
    private void switchMode() {
        fullscreen = !fullscreen;
        try {
            Display.setFullscreen(fullscreen);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Esta es la función que dibuja todos los objetos en el sistema 
     * gráfico OpenGL
     * @return
     */
    private boolean render() {

    	this.posicionarCamaraXZ();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer

        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
        /**
         * Dibujar la cuadrícula
         */
        GL11.glCallList(cuadricula);
        /**
         * Dibujar el modelo
         */

        /**
         * Generar los modelos
         */
        Grupo gr;


		gr = (Grupo)mapGrupo.get("Base");
        GL11.glCallList(modelo[gr.idGrupo]);

        /**
         * Controlar que los servos no se pasen del angulo máximo de rotación
         */

    	if(!(this.angleBaseHexagonal>=-1*this.rotacionMaximaBaseHexagonal/2))
    		angleBaseHexagonal = -1*this.rotacionMaximaBaseHexagonal/2;

    	if(!(this.angleBaseHexagonal<=this.rotacionMaximaBaseHexagonal/2))
    		angleBaseHexagonal = this.rotacionMaximaBaseHexagonal/2;

    	if(!(this.angleBrazo>=-1*this.rotacionMaximaBrazo/2))
    		angleBrazo = -1*this.rotacionMaximaBrazo/2;

    	if(!(this.angleBrazo<=this.rotacionMaximaBrazo/2))
    		angleBrazo = this.rotacionMaximaBrazo/2;

    	if(!(this.angleAnteBrazo>=-1*this.rotacionMaximaAnteBrazo/2))
    		angleAnteBrazo = -1*this.rotacionMaximaAnteBrazo/2;

    	if(!(this.angleAnteBrazo<=this.rotacionMaximaAnteBrazo/2))
    		angleAnteBrazo = this.rotacionMaximaAnteBrazo/2;

    	if(!(this.angleMano>=-1*this.rotacionMaximaMano/2))
    		angleMano = -1*this.rotacionMaximaMano/2;

    	if(!(this.angleMano<=this.rotacionMaximaMano/2))
    		angleMano = this.rotacionMaximaMano/2;

    	if(!(this.angleDedo1<=this.rotacionMaximaDedo1/2))
    		angleDedo1 = this.rotacionMaximaDedo1/2;

    	if(!(this.angleDedo1>=-1*this.rotacionMaximaDedo1/2))
    		angleDedo1 = -1*this.rotacionMaximaDedo1/2;

		/**
		 * BaseHexagonal
		 */
		GL11.glTranslatef(pivotBaseHexagonalX,pivotBaseHexagonalY,pivotBaseHexagonalZ);
		GL11.glRotatef(angleBaseHexagonal,0,1,0);
		GL11.glTranslatef(pivotBaseHexagonalX,-1*pivotBaseHexagonalY,-1*pivotBaseHexagonalZ);


		gr = (Grupo)mapGrupo.get("BaseHexagonal");
        GL11.glCallList(modelo[gr.idGrupo]);


		/**
		 * AnteBrazo
		 */
		GL11.glTranslatef(pivotAnteBrazoX,pivotAnteBrazoY,pivotAnteBrazoZ);
		GL11.glRotatef(angleAnteBrazo,1,0,0);
		GL11.glTranslatef(pivotAnteBrazoX,-1*pivotAnteBrazoY,-1*pivotAnteBrazoZ);

        gr = (Grupo)mapGrupo.get("AnteBrazo");
        GL11.glCallList(modelo[gr.idGrupo]);
        

		/**
		 * Brazo
		 */
		GL11.glTranslatef(pivotBrazoX,pivotBrazoY,pivotBrazoZ);
		GL11.glRotatef(angleBrazo,1,0,0);
		GL11.glTranslatef(pivotBrazoX,-1*pivotBrazoY,-1*pivotBrazoZ);

        gr = (Grupo)mapGrupo.get("Brazo");
        GL11.glCallList(modelo[gr.idGrupo]);

        /**
         * Mano
         */

		GL11.glTranslatef(pivotManoX,pivotManoY,pivotManoZ);
		GL11.glRotatef(angleMano,1,0,0);
		GL11.glTranslatef(pivotManoX,-1*pivotManoY,-1*pivotManoZ);

        gr = (Grupo)mapGrupo.get("mano");
        GL11.glCallList(modelo[gr.idGrupo]);

        /**
         * Dedo1
         */
		GL11.glTranslatef(pivotDedo1X,pivotDedo1Y,pivotDedo1Z);
		GL11.glRotatef(angleDedo1,0,1,0);
		GL11.glTranslatef(pivotDedo1X,-1*pivotDedo1Y,-1*pivotDedo1Z);

        gr = (Grupo)mapGrupo.get("dedo1");
        GL11.glCallList(modelo[gr.idGrupo]);

        /**
         * Dedo2
         */

		GL11.glTranslatef(pivotDedo2X,pivotDedo2Y,pivotDedo2Z);
		GL11.glRotatef(-angleDedo1 - angleDedo1,0,1,0);
		GL11.glTranslatef(pivotDedo2X,-1*pivotDedo2Y,-1*pivotDedo2Z);

        gr = (Grupo)mapGrupo.get("dedo2");
        GL11.glCallList(modelo[gr.idGrupo]);
        
        
        /**
         * objeto
         */
        if(this.claveActual.objetoTomado==0){
        	GL11.glLoadIdentity();
        	GL11.glTranslatef(this.objetoPosX,this.objetoPosY,this.objetoPosZ);
        }else
        {
        	
        	GL11.glTranslatef(this.pivotDedo2X+0.25f,this.pivotDedo2Y,this.pivotDedo2Z+3.3199f);
        	
        }
        
        
        gr = (Grupo)mapGrupo.get("balin");
        GL11.glCallList(modelo[gr.idGrupo]);
        
        GL11.glLoadIdentity();

        this.mostrarInfo();



        GL11.glLoadIdentity(); 
        GL11.glTranslatef(-1, 0, -0.5f); 



        return true;

    }
    /**
     * Crea la ventana inicial
     * @throws Exception
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(fullscreen);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == this.anchoPantalla
                && d[i].getHeight() == this.altoPantalla
                && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle(windowTitle);
        Display.create();
    }
    /**
     * Inicializa el subsistema de ventanas y el sistema gráfico OpenGL
     * @throws Exception
     */
    private void init() throws Exception {
    	
        createWindow();

        initGL();
        IL.create();
        loadTextures();




        crearCuadricula(anchoCuadricula,divCuadricula);
        leerObj(fileObj);


		docConfigBuilderFactory = DocumentBuilderFactory.newInstance();
		docConfigBuilder = docConfigBuilderFactory.newDocumentBuilder();
		docConfig = docConfigBuilder.parse (new File(archivoConfiguracion));
		docConfig.getDocumentElement ().normalize ();

		docAnimacionBuilderFactory = DocumentBuilderFactory.newInstance();
		docAnimacionBuilder = docAnimacionBuilderFactory.newDocumentBuilder();
		docAnimacion = docAnimacionBuilder.parse (new File(archivoAnimacion));
		docAnimacion.getDocumentElement ().normalize ();

		this.leerConfiguracion();
		this.leerAnimacion();
		this.pararseEnClave(1);

		this.cPrev = (Clave)this.mapClaves.get("1");
		this.cNext = (Clave)this.mapClaves.get(Integer.toString(this.cPrev.siguienteClave));
		
		

    }
    /**
     * Inicialización del sistema grafico OpenGL
     * */

    private void initGL() {
    	
    	bb.order(ByteOrder.nativeOrder());
    	
    	
    	GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.0f,30.0f,-30.0f,1.0f}).flip());
    	
    	
    	
    	
    	
        GL11.glEnable(GL11.GL_LIGHT0);
        
        
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading
        //GL11.glShadeModel(GL11.GL_FLAT); // Enable Smooth Shading
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        GL11.glClearDepth(1.0); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        //GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix

        /**
         * Calculate The Aspect Ratio Of The Window 
         */
        GLU.gluPerspective(
          80.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          1000.0f);


      	GLU.gluLookAt(23,23,23,0,0,0,0,1,0);




        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix


        
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);


    }
    private static void cleanup() {
        Display.destroy();
    }


    /**
     * Dibuja la cuadícula
     * largo, es el tamaño total de la cuadricula
     * div, es el tamaño de la subdivisión de la cuadrícula
     * */
	private void crearCuadricula(float largo,float div)
	{



		cuadricula = GL11.glGenLists(1);

		GL11.glNewList(cuadricula,GL11.GL_COMPILE);

			/**
			 * Dibujar la cuadricula
			 */

		
		GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.7f,0.7f,0.7f,1.0f}).flip());
		GL11.glMaterial(GL11.GL_FRONT,GL11.GL_DIFFUSE,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.7f,0.7f,0.7f,1.0f}).flip());

			GL11.glLineWidth(1f);

			GL11.glBegin(GL11.GL_LINES);
			for(float i= -1*(largo/2);i<=(largo/2);i+=div)
			{
				GL11.glVertex3f(i,0,-1*(largo/2));
				GL11.glVertex3f(i,0,largo/2 );

				GL11.glVertex3f(-1*(largo/2),0,i);
				GL11.glVertex3f(largo/2,0,i);
			}
			GL11.glEnd();


			GL11.glLineWidth(1.3f);

			GL11.glBegin(GL11.GL_LINES);
			for(float i= -1*(largo/2);i<=(largo/2);i+=(largo/2))
			{
				GL11.glVertex3f(i,0,-1*(largo/2));
				GL11.glVertex3f(i,0,largo/2 );

				GL11.glVertex3f(-1*(largo/2),0,i);
				GL11.glVertex3f(largo/2,0,i);
			}
			GL11.glEnd();

			GL11.glLineWidth(3.5f);

			/**
			 * Dibujar los ejes de coordenadas
			 */
			
			
			GL11.glBegin(GL11.GL_LINES);

			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_DIFFUSE,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0f,1.0f}).flip());
				
			//GL11.glColor3f(0.0f,0.0f,1.0f);
			GL11.glVertex3f(-1*largo/2,0,0);
			GL11.glVertex3f(largo/2,0,0);


				

			//GL11.glColor3f(0.0f,0.0f,1.0f);
			
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.0f,0.0f,1.0f,1.0f}).flip());
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_DIFFUSE,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.0f,0.0f,1.0f,1.0f}).flip());
			GL11.glVertex3f(0f,0f,-1*largo/2f);
			GL11.glVertex3f(0f,0f,largo/2);
			GL11.glEnd();


		GL11.glEndList();
	}


	/**
	 * Lee la información del modelo, la cual se genero
	 * con Maya y se exporto a un archivo de datos .obj
	 * lFileObj, es la ruta del modelo en formato obj.
	 * */
	private void leerObj(String lFileObj){

		String nombreMaterial="";
		String nmGrupo = "";
		float kdR,kdG,kgB;



		BufferedReader entradaObj = null;
		BufferedReader entradaMaterial = null;
		String lineaObj = null;
		String lineaMaterial = null;
		try{


			/**
			 * Leer el archivo OBJ
			 */
			entradaObj = new BufferedReader( new FileReader(lFileObj) );
			int v = 1;
			int f = 1;
			int idGrupo = 1;
			while ((lineaObj = entradaObj.readLine()) != null){
				/**
				 * Leer los vertices
				 */
				String[] partesLineaObj = lineaObj.split(" ");

				/**
				 * Archivo de materiales
				 */
				if (partesLineaObj[0].compareToIgnoreCase("mtllib")==0){
					/**
					 * Leer el archivo de Materiales
					 */
					fileMaterial = partesLineaObj[1];
					entradaMaterial = new BufferedReader( new FileReader(fileMaterial));

					while((lineaMaterial = entradaMaterial.readLine()) != null){
						String[] partesLineaMaterial = lineaMaterial.split(" ");
						/**
						 * Guarda los nombres de los materiales en un vector
						 */
						if (partesLineaMaterial[0].compareToIgnoreCase("newmtl")==0){
							nombreMaterial = partesLineaMaterial[1];

						}
						/**
						 * Guarda la información del color difuso de un material en el vector
						 */
						if (partesLineaMaterial[0].compareToIgnoreCase("kd")==0){
							Material material = new Material();
							material.nombre = nombreMaterial;
							material.difuseR = Float.valueOf((String)partesLineaMaterial[1]).floatValue();
							material.difuseG = Float.valueOf((String)partesLineaMaterial[2]).floatValue();
							material.difuseB = Float.valueOf((String)partesLineaMaterial[3]).floatValue();
							mapMaterial.put(nombreMaterial,material);

						}
					}
				}

				/**
				 * Estructuras de las lineas que tienen información de los vertices en el archivo OBJ
				 * v x y z
				 */
				
				if (partesLineaObj[0].compareToIgnoreCase("v")==0){
					Vertice vertice = new Vertice();
					vertice.x = Float.valueOf((String)partesLineaObj[1]).floatValue();
					vertice.y = Float.valueOf((String)partesLineaObj[2]).floatValue();
					vertice.z = Float.valueOf((String)partesLineaObj[3]).floatValue();
					mapVertice.put(Integer.toString(v),vertice);
					v++;
				}

				/**
				 * Siguiente material que se aplica a las siguientes caras
				 */
				if (partesLineaObj[0].compareToIgnoreCase("usemtl")==0){
					nombreMaterial =partesLineaObj[1];
				}




				/**
				 * Siguiente grupo al cual pertenecen las proximas caras
				 */

				if (partesLineaObj[0].compareToIgnoreCase("g")==0){


					if(mapGrupo.containsKey(partesLineaObj[1])==false && (partesLineaObj[1].compareTo("default")!=0)){
						nmGrupo = partesLineaObj[1];
						Grupo grupo = new Grupo();
						grupo.nombre = nmGrupo;
						grupo.idGrupo = idGrupo;
						mapGrupo.put(nmGrupo,grupo);
						idGrupo++;
					
					}

				}

				/**
				 * Leer las caras
				 * Estructuras de las lineas que tienen infroamción de las caras en el archivo OBJ
				 * f v/vt/vn v/vt/vn v/vt/vn v/vt/vn
				 */
				

				if (partesLineaObj[0].compareToIgnoreCase("f")==0){

					Cara cara = new Cara();
					cara.material = nombreMaterial;
					cara.grupo = nmGrupo;
					cara.numeroVertices = partesLineaObj.length - 1;

					for (int j=1;j<=partesLineaObj.length-1;j++)
					{

						String[] facePart = partesLineaObj[j].split("/");
						cara.idVertice[j-1] = Integer.valueOf((String)facePart[0]).intValue();



					}

					mapCara.put(Integer.toString(f),cara);
					f++;

				}


      		}




			/**
			 * Generar el modelo en OpenGL
			 */

			Set keyGrupo = mapGrupo.keySet();
			Iterator iteratorGrupo = keyGrupo.iterator();

			while(iteratorGrupo.hasNext()){


				String nmKeyGrupo = (String)iteratorGrupo.next();
				Grupo grupo = (Grupo)mapGrupo.get(nmKeyGrupo);



				modelo[grupo.idGrupo] = GL11.glGenLists(grupo.idGrupo);
				
				GL11.glNewList(modelo[grupo.idGrupo],GL11.GL_COMPILE);
				Set keyCara = mapCara.keySet();
				Iterator iteratorCara = keyCara.iterator();
				
				ByteBuffer bb = ByteBuffer.allocateDirect(16);
		        bb.order(ByteOrder.nativeOrder());
		        
		        //GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.3f,0.3f,0.3f,1.0f}).flip());
				
				
		        
				while(iteratorCara.hasNext()){

					String idCara = (String)iteratorCara.next();
					Cara cara = (Cara)mapCara.get(idCara);


					Material material = (Material)mapMaterial.get(cara.material);
					
					
					
					//ByteBuffer ba = ByteBuffer.allocateDirect(1024);
					//FloatBuffer materialAmbient = ba.asFloatBuffer();
					
					
					
					//materialDifuso.put(new float[] {material.difuseR,material.difuseG,material.difuseB,1.0f});
					//GL11.glMaterial(GL11.GL_FRONT_AND_BACK,GL11.GL_DIFFUSE,materialDifuso);
					
					//GL11.glMaterial(GL11.GL_FRONT,GL11.GL_DIFFUSE,(FloatBuffer)bb.asFloatBuffer().put(new float[] {material.difuseR,material.difuseG,material.difuseB,1.0f}).flip());
					GL11.glMaterial(GL11.GL_FRONT,GL11.GL_DIFFUSE,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.1f*material.difuseR,0.1f*material.difuseG,0.1f*material.difuseB,1.0f}).flip());
					GL11.glMaterial(GL11.GL_FRONT,GL11.GL_AMBIENT,(FloatBuffer)bb.asFloatBuffer().put(new float[] {material.difuseR,material.difuseG,material.difuseB,1.0f}).flip());
					GL11.glMaterial(GL11.GL_FRONT,GL11.GL_SPECULAR,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
					GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f*material.difuseR,1.0f*material.difuseG,1.0f*material.difuseB,1.0f}).flip());
					//GL11.glMaterial(GL11.GL_FRONT,GL11.GL_SHININESS,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.0f,0.0f,0.0f,1.0f}).flip());
					
					
					//GL11.glColor3f(material.difuseR,material.difuseG,material.difuseB);
					
					//materialAmbient.put(new float[] {material.difuseR,material.difuseG,material.difuseB});
					
					GL11.glBegin(GL11.GL_POLYGON );
					//GL11.glBegin(GL11.GL_QUADS);



					if (cara.grupo.compareToIgnoreCase(grupo.nombre)==0){

						for(v=0;v<=cara.numeroVertices-1;v++ )

						{


							Vertice vertice = (Vertice)mapVertice.get(Integer.toString(cara.idVertice[v]));
							GL11.glVertex3f(vertice.x,vertice.y,vertice.z);


						}
					}

					GL11.glEnd();




				}
				GL11.glEndList();

			}





		}catch(Exception e){
			System.out.println(e.toString());
		}

	}

	/**
	 * Lee la información inicial de el
	 * archivo de configuración en formato XML
	 * */

	void leerConfiguracion(){

		/**
		 * Leer los valores iniciales de la cámara
		 */
		this.distanciaCamaraObjetivo = Float.parseFloat(this.leerInfoSubNodoConfiguracion("camara","nombre","camaraprincipal","distanciacamaraobjetivo",docConfig));
		this.angleCamaraXZ =  Float.parseFloat(this.leerInfoSubNodoConfiguracion("camara","nombre","camaraprincipal","anguloxz",docConfig));
		this.angleCamaraXY =  Float.parseFloat(this.leerInfoSubNodoConfiguracion("camara","nombre","camaraprincipal","anguloxy",docConfig));
		this.offsetAnguloCamara =  Float.parseFloat(this.leerInfoSubNodoConfiguracion("camara","nombre","camaraprincipal","pasorotacion",docConfig));
		this.distanciaCamaraInfo = Float.parseFloat(this.leerInfoSubNodoConfiguracion("camara","nombre","camaraprincipal","distanciacamarainfo",docConfig));


		/**
		 * Leer los angulos iniciales de los grupos
		 */

		this.angleAnteBrazo = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","AnteBrazo","anguloinicial",docConfig));
		this.pivotAnteBrazoX = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","AnteBrazo","pivotx",docConfig));
		this.pivotAnteBrazoY = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","AnteBrazo","pivoty",docConfig));
		this.pivotAnteBrazoZ = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","AnteBrazo","pivotz",docConfig));
		this.offsetRotacionAnteBrazo = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","AnteBrazo","pasorotacion",docConfig));
		this.rotacionMaximaAnteBrazo = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","AnteBrazo","rotacionmaxima",docConfig));



		this.angleBrazo = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","Brazo","anguloinicial",docConfig));
		this.pivotBrazoX = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","Brazo","pivotx",docConfig));
		this.pivotBrazoY = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","Brazo","pivoty",docConfig));
		this.pivotBrazoZ = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","Brazo","pivotz",docConfig));
		this.offsetRotacionBrazo = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","Brazo","pasorotacion",docConfig));
		this.rotacionMaximaBrazo = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","Brazo","rotacionmaxima",docConfig));

		this.angleBaseHexagonal = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","BaseHexagonal","anguloinicial",docConfig));
		this.pivotBaseHexagonalX = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","BaseHexagonal","pivotx",docConfig));
		this.pivotBaseHexagonalY = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","BaseHexagonal","pivoty",docConfig));
		this.pivotBaseHexagonalZ = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","BaseHexagonal","pivotz",docConfig));
		this.offsetRotacionBaseHexagonal = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","BaseHexagonal","pasorotacion",docConfig));
		this.rotacionMaximaBaseHexagonal = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","BaseHexagonal","rotacionmaxima",docConfig));

		this.angleMano = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","mano","anguloinicial",docConfig));
		this.pivotManoX = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","mano","pivotx",docConfig));
		this.pivotManoY = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","mano","pivoty",docConfig));
		this.pivotManoZ = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","mano","pivotz",docConfig));
		this.offsetRotacionMano = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","mano","pasorotacion",docConfig));
		this.rotacionMaximaMano = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","mano","rotacionmaxima",docConfig));

		this.angleDedo1 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo1","anguloinicial",docConfig));
		this.pivotDedo1X = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo1","pivotx",docConfig));
		this.pivotDedo1Y = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo1","pivoty",docConfig));
		this.pivotDedo1Z = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo1","pivotz",docConfig));
		this.offsetRotacionDedo1 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo1","pasorotacion",docConfig));
		this.rotacionMaximaDedo1 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo1","rotacionmaxima",docConfig));

		this.angleDedo2 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo2","anguloinicial",docConfig));
		this.pivotDedo2X = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo2","pivotx",docConfig));
		this.pivotDedo2Y = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo2","pivoty",docConfig));
		this.pivotDedo2Z = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo2","pivotz",docConfig));
		this.offsetRotacionDedo2 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo2","pasorotacion",docConfig));
		this.rotacionMaximaDedo2 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("grupo","nombre","dedo2","rotacionmaxima",docConfig));
		
		/**
		 * Leer la posición inicial del objeto
		 */
		this.objetoPosX = Float.parseFloat(this.leerInfoSubNodoConfiguracion("objeto","nombre","bola","objetoPosX",docConfig));
		this.objetoPosY = Float.parseFloat(this.leerInfoSubNodoConfiguracion("objeto","nombre","bola","objetoPosY",docConfig));
		this.objetoPosZ = Float.parseFloat(this.leerInfoSubNodoConfiguracion("objeto","nombre","bola","objetoPosZ",docConfig));
		
		/**
		 * Lee la información de los puertos
		 */
		
		this.idPuerto = this.leerInfoSubNodoConfiguracion("puerto","tipo","serial","idpuerto",this.docConfig);
		this.baudRate = Integer.parseInt(this.leerInfoSubNodoConfiguracion("puerto","tipo","serial","baudrate",this.docConfig));
		

		}
	/**
	 * Lee la anaimación que se guarda en un archivo XML
	 * Inicialmente solo trabajaremos con 4 claves
	 * */
	void leerAnimacion(){
			int nc=1;
			while(true){
				Clave clave = new Clave();
				clave.id = nc;
				clave.servo0 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("clave","id",Integer.toString(nc),"servo0",docAnimacion));
				clave.servo1 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("clave","id",Integer.toString(nc),"servo1",docAnimacion));
				clave.servo2 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("clave","id",Integer.toString(nc),"servo2",docAnimacion));
				clave.servo3 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("clave","id",Integer.toString(nc),"servo3",docAnimacion));
				clave.servo4 = Float.parseFloat(this.leerInfoSubNodoConfiguracion("clave","id",Integer.toString(nc),"servo4",docAnimacion));
				clave.objetoTomado = Integer.parseInt(this.leerInfoSubNodoConfiguracion("clave","id",Integer.toString(nc),"objetoTomado",docAnimacion));
				clave.siguienteClave = Integer.parseInt(this.leerInfoSubNodoConfiguracion("clave","id",Integer.toString(nc),"siguienteclave",docAnimacion));
				clave.tipo = this.leerInfoSubNodoConfiguracion("clave","id",Integer.toString(nc),"tipo",docAnimacion);
				mapClaves.put(Integer.toString(nc),clave);

				if (clave.siguienteClave==1){
					this.numClaves = nc;
					return;
				}
				nc++;
			}




	}
	
	/**
	 * Ejecuta la animación desde el cuadro 1 hasta el ultimo cuadro
	 * */
	
	public void ejecutarMovimiento(){
		

		
		
		if(this.angleBaseHexagonal!=cNext.servo0){
			this.isMovingServo0 = true;
			if(cPrev.servo0<=cNext.servo0)

				this.angleBaseHexagonal+=this.offsetRotacionBaseHexagonal;
			else
				this.angleBaseHexagonal-=this.offsetRotacionBaseHexagonal;
		}else{
			this.isMovingServo0 = false;
		}

		if(this.angleAnteBrazo!=cNext.servo1){
			this.isMovingServo1 = true;
			if(cPrev.servo1<=cNext.servo1)

				this.angleAnteBrazo+=this.offsetRotacionAnteBrazo;
			else
				this.angleAnteBrazo-=this.offsetRotacionAnteBrazo;
		}else{
			this.isMovingServo1 = false;
		}

		if(this.angleBrazo!=cNext.servo2){
			this.isMovingServo2 = true;
			if(cPrev.servo2<=cNext.servo2)

				this.angleBrazo+=this.offsetRotacionBrazo;
			else
				this.angleBrazo-=this.offsetRotacionBrazo;
		}else{
			this.isMovingServo2 = false;
		}

		if(this.angleMano!=cNext.servo3){
			this.isMovingServo3 = true;
			if(cPrev.servo3<=cNext.servo3)

				this.angleMano+=this.offsetRotacionMano;
			else
				this.angleMano-=this.offsetRotacionMano;
		}else{
			this.isMovingServo3 = false;
		}

		if(this.angleDedo1!=cNext.servo4){
			this.isMovingServo4 = true;
			if(cPrev.servo4<=cNext.servo4)

				this.angleDedo1+=this.offsetRotacionDedo1;
			else
				this.angleDedo1-=this.offsetRotacionDedo1;
		}else{
			this.isMovingServo4 = false;
		}

		if(!this.isMovingServo0&&!this.isMovingServo1&&!this.isMovingServo2&&!this.isMovingServo3&&!this.isMovingServo4){

			this.cPrev = (Clave)this.mapClaves.get(Integer.toString(this.cPrev.siguienteClave));
			this.claveActual = this.cPrev;
			this.cNext = (Clave)this.mapClaves.get(Integer.toString(this.cPrev.siguienteClave));

		}

		if (this.claveActual.tipo.compareTo("FINAL")==0){

			this.ejecutarAnimacion =false;

		}
		

		

	}

	/**
	 * Devuelve el valor de un subNodo de la configuración
	 *
	 */
	
	String leerInfoSubNodoConfiguracion(String nmNodo,String nmSubNodoBusqueda,String valSubNodoBusqueda,String nmSubNodoResultado,Document docXml){
		String valSubNodoResultado="";
	    try {


			/**
			 * Leer la información inicial del nodo
			 * 
			 */
            
            NodeList listaNodos = docXml.getElementsByTagName(nmNodo);



            for(int s=0; s<listaNodos.getLength() ; s++){


                Node nodo = listaNodos.item(s);
                Element elemento;
                NodeList elementoList;
                Element elementoElement;
                NodeList ndList;



                if(nodo.getNodeType() == Node.ELEMENT_NODE){

					//Leer el valor del subnodo
					elemento = (Element)nodo;
					elementoList = elemento.getElementsByTagName(nmSubNodoBusqueda);
					elementoElement = (Element)elementoList.item(0);
					ndList = elementoElement.getChildNodes();


					if(((Node)ndList.item(0)).getNodeValue().compareToIgnoreCase(valSubNodoBusqueda)==0){
						elemento = (Element)nodo;
						elementoList = elemento.getElementsByTagName(nmSubNodoResultado);
						elementoElement = (Element)elementoList.item(0);
						ndList = elementoElement.getChildNodes();
						valSubNodoResultado = ((Node)ndList.item(0)).getNodeValue();
						return valSubNodoResultado;
						}


                }


            }



        }catch (Exception e) {

			System.out.println(e.getMessage());
			return e.getMessage();
        }

        return valSubNodoResultado;

	}
	
	/**Ubica la cámara en el plano XZ de acuerdo de acuerdo
	 * al angulo que se incrementa o decrementa en el eje y.
	 * */
	
	void posicionarCamaraXZ(){
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Selecciona la matriz de proyección
        GL11.glLoadIdentity(); // Inicializa la matriz de proyección

        // Calcula Aspect Ratio de la ventana
        GLU.gluPerspective(
          80.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          1000.0f);
        this.posCamaraX = this.distanciaCamaraObjetivo*(float)Math.sin(this.angleCamaraXZ*Math.PI/360) + this.posObjetivoX;
        this.posCamaraZ = this.distanciaCamaraObjetivo*(float)Math.cos(this.angleCamaraXZ*Math.PI/360) + this.posObjetivoZ;


      	GLU.gluLookAt(this.posCamaraX,this.posCamaraY,this.posCamaraZ,this.posObjetivoX,this.posObjetivoY,this.posObjetivoZ,0,1,0);

        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Selecciona la matriz de modelview
        
        //Posicionar la luz en el objetivo de la cámara
        //GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {this.posCamaraX,this.posCamaraY,this.posCamaraZ,1.0f}).flip());
        //GL11.glLight(GL11.GL_LIGHT0,GL11.GL_SPOT_DIRECTION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {this.posObjetivoX,this.posObjetivoY,this.posObjetivoZ,1.0f}).flip());
        
	}

	void posicionarCamaraXY(){
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Selecciona la matriz de proyección
        GL11.glLoadIdentity(); // Limpia la matriz de proyección

        // Calcula el aspect ratio de la ventana
        GLU.gluPerspective(
          80.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          1000.0f);
        this.posCamaraX = this.distanciaCamaraObjetivo*(float)Math.cos(this.angleCamaraXY*Math.PI/360) + this.posObjetivoX;

        this.posCamaraY = this.distanciaCamaraObjetivo*(float)Math.sin(this.angleCamaraXY*Math.PI/360) + this.posObjetivoY;


      	GLU.gluLookAt(this.posCamaraX,this.posCamaraY,this.posCamaraZ,this.posObjetivoX,this.posObjetivoY,this.posObjetivoZ,0,1,0);

        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Selecciona la matriz modelview
	}


    /** Muestra los diferentes comnados que se pueden ejecutar
     * y sus diferentes short cuts
     * TY -> Mover camara en eje Y
     * RY -> Rotar la camara en el eje y al rededor del objetivo
     * Z -> Acercarse alejarse
     * S0 -> Rotar servo 0
     * S1 -> Rotar servo 1
     * S2 -> Rotar servo 2
     * S3 -> Rotar servo 3
     * S4 -> Rotar servo 4
     * Ns -> Navegar entre los cuadros claves de 1 en 1
     * N1 -> Pararse en el primer cuadro clave
     * GC -> Guardar cuadro clave con la informacion actual
     * P -> Ejecutar el movimiento en forma virtual
     * T -> Transmitir la el movimiento al puerto de comunicaciones
     * To -> Tomar objeto
     * Lo -> Liberar objeto 
     * F -> Gaurdar movimientos en archivo XML
     * */
    private void mostrarInfo(){
    	float altoRectangulo = 1.2f;
    	float anchoRectangulo = 4.5f;
    	float centroRectanguloX = 19.3f;
    	float centroRectanguloY = 15.3f;
    	float centroRectanguloZ = 0.0f;
    	float deltaY = 1.8f;
    	


    	GL11.glLoadIdentity();

        float x = (this.distanciaCamaraObjetivo-this.distanciaCamaraInfo)*(float)Math.sin(this.angleCamaraXZ*Math.PI/360) + this.posObjetivoX;
        float z = (this.distanciaCamaraObjetivo-this.distanciaCamaraInfo)*(float)Math.cos(this.angleCamaraXZ*Math.PI/360) + this.posObjetivoZ;
        float y = (this.distanciaCamaraObjetivo-this.distanciaCamaraInfo)*(float)Math.sin(this.angleCamaraXY*Math.PI/360) + this.posObjetivoY;

        GL11.glColor3f(0,1,0);
    	GL11.glTranslatef(x,y,z);
    	GL11.glRotatef(this.angleCamaraXZ/2,0,1,0);
    	GL11.glRotatef(-1*this.angleCamaraXY/2,1,0,0);
    	
    	GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.0f,1.0f,0.0f,1.0f}).flip());
		GL11.glMaterial(GL11.GL_FRONT,GL11.GL_DIFFUSE,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.0f,1.0f,0f,1.0f}).flip());

    	//Panel Central
    	GL11.glLineWidth(0.5f);

    	GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex3f(-16,12,0);
			GL11.glVertex3f(16,12,0);
			GL11.glVertex3f(16,-12,0);
			GL11.glVertex3f(-16,-12,0);
		GL11.glEnd();

		GL11.glColor3f(0,1,0);
		GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex3f(-16.2f,12.2f,0);
			GL11.glVertex3f(16.2f,12.2f,0);
			GL11.glVertex3f(16.2f,-12.2f,0);
			GL11.glVertex3f(-16.2f,-12.2f,0);
		GL11.glEnd();

		//Indicadores
		//TY
		if (moverSobreEjeY==true){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnMoverY[1]);
			
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY,centroRectanguloZ);

		}
		else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnMoverY[1]);
			
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY,centroRectanguloZ);
			
		}


		//RY
		
		if(rotarSobreEjeY==true){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnRotarY[1]);
			
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*1,centroRectanguloZ);

		}

		else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnRotarY[1]);
			
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*1,centroRectanguloZ);
			
		}

		//Z
		if (zoom==true){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnAcercar[1]);
			
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*2,centroRectanguloZ);

		}
		else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnAcercar[1]);
			
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*2,centroRectanguloZ);

		}

		//S0
		if(this.rotarServo0){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo0[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*3,centroRectanguloZ);

		}
		else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo0[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*3,centroRectanguloZ);

		
		}

		//S1
		if(this.rotarServo1){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo1[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*4,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo1[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*4,centroRectanguloZ);

		}

		//S2
		if(this.rotarServo2){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo2[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*5,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo2[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*5,centroRectanguloZ);

		}


		//S3
		if(this.rotarServo3){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo3[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*6,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo3[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*6,centroRectanguloZ);

		}


		//S4
		if(this.rotarServo4){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo4[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*7,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnServo4[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*7,centroRectanguloZ);

		}



		//NS
		if(this.irSiguienteCuadro){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnSiguienteCuadro[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*8,centroRectanguloZ);
		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnSiguienteCuadro[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*8,centroRectanguloZ);



		}



    	
		//N1
		if(this.irPrimerCuadro){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnCuadro1[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*9,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnCuadro1[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*9,centroRectanguloZ);


		}



		//GC
		if(this.guardarClave){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnGuardarClave[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*10,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnGuardarClave[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*10,centroRectanguloZ);

		}



    	

		//P
		if(this.ejecutarAnimacion){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnEjecutarMovimiento[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*11,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnEjecutarMovimiento[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*11,centroRectanguloZ);

		}


		//T
		if(this.transmitirPuerto){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnTransmisionSerial[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*12,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnTransmisionSerial[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*12,centroRectanguloZ);

		}

		//To
		if(this.tomarObjeto){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnTomarObjeto[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*13,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnTomarObjeto[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*13,centroRectanguloZ);

		}	

		//Lo
		if(this.soltarObjeto){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnLiberarObjeto[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*14,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnLiberarObjeto[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*14,centroRectanguloZ);

		}	
		
		//F
		if(this.guardarMovimientoXml){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnGuardarXml[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*15,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnGuardarXml[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*15,centroRectanguloZ);
	
		}	
		
		//C
		
		if(this.copiarClave){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnCopiarClave[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*16,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnCopiarClave[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*16,centroRectanguloZ);
	
		}	
		
		//V
		if(this.pegarClave){
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,0.0f,0.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnPegarClave[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*17,centroRectanguloZ);

		}else{
			GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, btnPegarClave[1]);
			this.rectangulo(anchoRectangulo,altoRectangulo,centroRectanguloX,centroRectanguloY-deltaY*17,centroRectanguloZ);
	
		}			
		

		//GL11.glColor3f(1,1,1);
    	GL11.glLoadIdentity();

    }

    /** Dibuja las letras que se mostrarán en la pantalla
     * para ejecutar los diferentes comandos
     * */
    void dibujarCarater(String letra,float segmentoCaracter,float segmentoCaracterLite,float centroX,float centroY,float centroZ){

    	boolean dibujarLinea1=false;
    	boolean dibujarLinea2=false;
    	boolean dibujarLinea3=false;
    	boolean dibujarLinea4=false;
    	boolean dibujarLinea5=false;
    	boolean dibujarLinea6=false;
    	boolean dibujarLinea7=false;
    	boolean dibujarLinea8=false;
    	boolean dibujarLinea9=false;
    	boolean dibujarLinea10=false;
    	boolean dibujarLinea11=false;
    	boolean dibujarLinea12=false;
    	boolean dibujarLinea13=false;
    	boolean dibujarLinea14=false;

    	if (letra.compareToIgnoreCase("8")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;
        	dibujarLinea9=true;
        	dibujarLinea10=true;
        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}
    	
    	if (letra.compareToIgnoreCase("F")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=false;
        	dibujarLinea4=false;
        	dibujarLinea5=false;
        	dibujarLinea6=false;
        	dibujarLinea7=false;
        	dibujarLinea8=false;
        	dibujarLinea9=true;
        	dibujarLinea10=true;
        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}    	
    	
    	if (letra.compareToIgnoreCase("l")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea5=false;
        	dibujarLinea6=false;
        	dibujarLinea7=false;
        	dibujarLinea8=false;
        	dibujarLinea9=false;
        	dibujarLinea10=false;
        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=false;
        	dibujarLinea14=false;
    	}
    	
    	if (letra.compareToIgnoreCase("o")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("n")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;

        	dibujarLinea5=true;
        	dibujarLinea6=true;

        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("3")==0){

        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;
        	dibujarLinea9=true;
        	dibujarLinea10=true;

        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("4")==0){

        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;

        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}




    	if (letra.compareToIgnoreCase("1")==0){

        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;

    	}

    	if (letra.compareToIgnoreCase("G")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea9=true;
        	dibujarLinea10=true;
        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("p")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;
        	dibujarLinea9=true;
        	dibujarLinea10=true;
        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("C")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;


    	}

    	if (letra.compareToIgnoreCase("0")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;
        	dibujarLinea9=true;
        	dibujarLinea10=true;
        	dibujarLinea11=true;
        	dibujarLinea12=true;

    	}

    	if (letra.compareToIgnoreCase("s")==0){

        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea9=true;
        	dibujarLinea10=true;
        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("T")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("z")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;
        	dibujarLinea9=true;
        	dibujarLinea10=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("2")==0){
        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;
        	dibujarLinea9=true;
        	dibujarLinea10=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("Y")==0){

        	dibujarLinea3=true;
        	dibujarLinea4=true;
        	dibujarLinea5=true;
        	dibujarLinea6=true;
        	dibujarLinea7=true;
        	dibujarLinea8=true;
        	dibujarLinea11=true;
        	dibujarLinea12=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;
    	}

    	if (letra.compareToIgnoreCase("R")==0){

        	dibujarLinea1=true;
        	dibujarLinea2=true;
        	dibujarLinea13=true;
        	dibujarLinea14=true;

    	}

    	GL11.glBegin(GL11.GL_LINES);
    		
    		//1
    		if (dibujarLinea1)
    			GL11.glVertex3f(centroX ,centroY + segmentoCaracter + segmentoCaracterLite,centroZ);
    		//2
    		if (dibujarLinea2)
    			GL11.glVertex3f(centroX,centroY + segmentoCaracterLite,centroZ);
    		//3
    		if (dibujarLinea3)
    			GL11.glVertex3f(centroX + segmentoCaracterLite,centroY,centroZ);
    		//4
    		if (dibujarLinea4)
    			GL11.glVertex3f(centroX + segmentoCaracter + segmentoCaracterLite,centroY,centroZ);
    		//5
    		if (dibujarLinea5)
    			GL11.glVertex3f(centroX + segmentoCaracter + 2*segmentoCaracterLite,centroY + segmentoCaracterLite,centroZ);
    		//6
    		if (dibujarLinea6)
    			GL11.glVertex3f(centroX + segmentoCaracter + 2*segmentoCaracterLite,centroY + segmentoCaracter + segmentoCaracterLite,centroZ);
    		//7
    		if (dibujarLinea7)
    			GL11.glVertex3f(centroX + segmentoCaracter + 2*segmentoCaracterLite,centroY + segmentoCaracter + 3*segmentoCaracterLite,centroZ);
    		//8
    		if (dibujarLinea8)
    			GL11.glVertex3f(centroX + segmentoCaracter + 2*segmentoCaracterLite,centroY + 2*segmentoCaracter + 3*segmentoCaracterLite,centroZ);
    		//9
    		if (dibujarLinea9)
    			GL11.glVertex3f(centroX + segmentoCaracter + segmentoCaracterLite,centroY + 2*segmentoCaracter + 4*segmentoCaracterLite,centroZ);
    		//10
    		if (dibujarLinea10)
    			GL11.glVertex3f(centroX + segmentoCaracterLite,centroY + 2*segmentoCaracter + 4*segmentoCaracterLite,centroZ);
    		//11
    		if (dibujarLinea11)
    			GL11.glVertex3f(centroX ,centroY + 2*segmentoCaracter + 3*segmentoCaracterLite,centroZ );
    		//12
    		if (dibujarLinea12)
    			GL11.glVertex3f(centroX ,centroY + segmentoCaracter + 3*segmentoCaracterLite,centroZ );
    		//13
    		if (dibujarLinea13)
    			GL11.glVertex3f(centroX + segmentoCaracterLite,centroY + segmentoCaracter + 2*segmentoCaracterLite,centroZ);
    		//14
    		if (dibujarLinea14)
    			GL11.glVertex3f(centroX + segmentoCaracter + segmentoCaracterLite,centroY + segmentoCaracter + 2*segmentoCaracterLite,centroZ);
    	GL11.glEnd();





    }
    /**Dibuja los rectangulos que se muestran en la pantalla con
     * la información de los comandos de las teclas
     * */

	void rectangulo(float ancho,float alto,float centroX,float centroY,float centroZ){

		//GL11.glMaterial(GL11.GL_FRONT,GL11.GL_EMISSION,(FloatBuffer)bb.asFloatBuffer().put(new float[] {0.0f,0.1f,0.0f,1.0f}).flip());
		
		GL11.glMaterial(GL11.GL_FRONT,GL11.GL_DIFFUSE,(FloatBuffer)bb.asFloatBuffer().put(new float[] {1.0f,1.0f,1.0f,1.0f}).flip());
		//GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(centroX - ancho/2 ,centroY - alto/2,centroZ);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(centroX + ancho/2,centroY - alto/2,centroZ);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(centroX + ancho/2,centroY + alto/2,centroZ);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(centroX - ancho/2,centroY + alto/2,centroZ);
		GL11.glEnd();
	}
	/**
	 * Activa o se para en una clave determinada
	 * */
	void pararseEnClave(int idClave){
		this.claveActual = (Clave)this.mapClaves.get(Integer.toString(idClave));
		this.angleBaseHexagonal = this.claveActual.servo0;
		this.angleAnteBrazo = this.claveActual.servo1;
		this.angleBrazo = this.claveActual.servo2;
		this.angleMano = this.claveActual.servo3;
		this.angleDedo1 = this.claveActual.servo4;
		this.angleDedo2 = this.claveActual.servo4;
		this.cPrev = this.claveActual;
		this.cNext = (Clave)this.mapClaves.get(Integer.toString(cPrev.siguienteClave));
		Display.setTitle(this.windowTitle + ",Clave actual(" + Integer.toHexString(this.claveActual.id) + " de " + this.numClaves + ")");
	}

	/**
	 * Guarda los valores de la clave actual en la animacion.
	 * */
	void guardarValoresClave(){
		mapClaves.remove(Integer.toString(this.claveActual.id));
		this.claveActual.servo0 = this.angleBaseHexagonal;
		this.claveActual.servo1 = this.angleAnteBrazo;
		this.claveActual.servo2 = this.angleBrazo;
		this.claveActual.servo3 = this.angleMano;
		this.claveActual.servo4 = this.angleDedo1;
		mapClaves.put(Integer.toString(this.claveActual.id),this.claveActual);
	}
	
	/**
	 * Guarda las claves en el archivo movimiento001.xml
	 * */
	
	void guardarMovimientoXml()
	
    	{

		String titulo = Display.getTitle();
		Display.setTitle("Guardando archivo movimiento001.xml");
		try {
			FileOutputStream fout = new FileOutputStream("movimiento001.xml");
			PrintStream st = new PrintStream(fout);
			st.println("<movimiento>");
			
			for(int k=0;k<this.mapClaves.size();k++){
				st.println("     <clave>");
				Clave cl = (Clave)mapClaves.get(Integer.toString(k+1));
				st.println("          <!--Identificador del cuadro-->");
				st.println("          <id>" + Integer.toString(k+1) + "</id>");
				st.println("          <!--Puede ser INICIO|INTERMEDIO|FINAL-->");
				st.println("          <tipo>" + cl.tipo + "</tipo>");
				st.println("          <!--Identificador del cuadro que sigue en la secuencia-->");
				st.println("          <siguienteclave>" + cl.siguienteClave + "</siguienteclave>");
				st.println("          <!--Grados del servo 0 en este cuadro-->");
				st.println("          <servo0>" + cl.servo0 + "</servo0>");
				st.println("          <!--Grados del servo 1 en este cuadro-->");
				st.println("          <servo1>" + cl.servo1 + "</servo1>");
				st.println("          <!--Grados del servo 2 en este cuadro-->");
				st.println("          <servo2>" + cl.servo2 + "</servo2>");
				st.println("          <!--Grados del servo 3 en este cuadro-->");
				st.println("          <servo3>" + cl.servo3 + "</servo3>");
				st.println("          <!--Grados del servo 4 en este cuadro-->");
				st.println("          <servo4>" + cl.servo4 + "</servo4>");
				st.println("          <!--Determina si el objeto ha sido tomado por el brazo-->");
				st.println("          <objetoTomado>" + cl.objetoTomado + "</objetoTomado>");
				st.println("     </clave>");
			}
			st.println("</movimiento>");
			fout.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally
		{
			Display.setTitle(titulo);
		}
		
	}
	
	/**
	 * Transmitir movimientos por el puerto serial 
	 * */
	
	
	void transmitirMovimientos(){
		
		try{
			if(this.transmitirPuerto)
			{
				String salida = "";
				CommPortIdentifier identificadorPuerto = CommPortIdentifier.getPortIdentifier(this.idPuerto);
				SerialPort puertoSerial = (SerialPort)identificadorPuerto.open("RobotArmControlerApp",3000);
				puertoSerial.setSerialPortParams(this.baudRate,
	                    SerialPort.DATABITS_8,
	                    SerialPort.STOPBITS_1,
	                    SerialPort.PARITY_NONE);
				OutputStream oStream = puertoSerial.getOutputStream();
				
				/**
				 * Colocar el brazo en la posición incial
				 */
				Clave clInicial = (Clave)mapClaves.get(Integer.toString(1));
				oStream.write((byte)255);
				oStream.write((byte)0);
				oStream.write((byte)this.gradosServo254((float)clInicial.servo0,0));
				
				oStream.write((byte)255);
				oStream.write((byte)1);
				oStream.write((byte)this.gradosServo254((float)clInicial.servo1,1));
				
				oStream.write((byte)255);
				oStream.write((byte)2);
				oStream.write((byte)this.gradosServo254((float)clInicial.servo2,2));
				
				oStream.write((byte)255);
				oStream.write((byte)3);
				oStream.write((byte)this.gradosServo254((float)clInicial.servo3,3));
				
				oStream.write((byte)255);
				oStream.write((byte)4);
				oStream.write((byte)this.gradosServo254((float)clInicial.servo4,4));
				

				for(int l=0;l<this.mapClaves.size();l++){
					
					
					Clave clActual = (Clave)mapClaves.get(Integer.toString(l+1));
					Clave clSiguienteClave = (Clave)mapClaves.get(Integer.toString(clActual.siguienteClave));
					
					
					
					if (this.gradosServo254((float)clActual.servo0,0)<this.gradosServo254((float)clSiguienteClave.servo0,0)){
						for(int angleSteep=this.gradosServo254((float)clActual.servo0,0);angleSteep<=this.gradosServo254((float)clSiguienteClave.servo0,0);angleSteep++){
							oStream.write((byte)255);
							oStream.write((byte)0);
							oStream.write((byte)angleSteep);
							
							//System.out.println(angleSteep);
						}
					}else{
						for(int angleSteep=this.gradosServo254((float)clActual.servo0,0);angleSteep>=this.gradosServo254((float)clSiguienteClave.servo0,0);angleSteep--){
							oStream.write((byte)255);
							oStream.write((byte)0);
							oStream.write((byte)angleSteep);
							
							//System.out.println(angleSteep);
						}
					}
					
					if (this.gradosServo254((float)clActual.servo1,1)<this.gradosServo254((float)clSiguienteClave.servo1,1)){
						for(int angleSteep=this.gradosServo254((float)clActual.servo1,1);angleSteep<=this.gradosServo254((float)clSiguienteClave.servo1,1);angleSteep++){
							oStream.write((byte)255);
							oStream.write((byte)1);
							oStream.write((byte)angleSteep);
							
			//				System.out.println(angleSteep);
						}
					}else{
						for(int angleSteep=this.gradosServo254((float)clActual.servo1,1);angleSteep>=this.gradosServo254((float)clSiguienteClave.servo1,1);angleSteep--){
							oStream.write((byte)255);
							oStream.write((byte)1);
							oStream.write((byte)angleSteep);
							
			//				System.out.println(angleSteep);
						}
					}	
					
					if (this.gradosServo254((float)clActual.servo2,2)<this.gradosServo254((float)clSiguienteClave.servo2,2)){
						for(int angleSteep=this.gradosServo254((float)clActual.servo2,2);angleSteep<=this.gradosServo254((float)clSiguienteClave.servo2,2);angleSteep++){
							oStream.write((byte)255);
							oStream.write((byte)2);
							oStream.write((byte)angleSteep);
							
			//				System.out.println(angleSteep);
						}
					}else{
						for(int angleSteep=this.gradosServo254((float)clActual.servo2,2);angleSteep>=this.gradosServo254((float)clSiguienteClave.servo2,2);angleSteep--){
							oStream.write((byte)255);
							oStream.write((byte)2);
							oStream.write((byte)angleSteep);
							
			//				System.out.println(angleSteep);
						}
					}					
					
					if (this.gradosServo254((float)clActual.servo3,3)<this.gradosServo254((float)clSiguienteClave.servo3,3)){
						for(int angleSteep=this.gradosServo254((float)clActual.servo3,3);angleSteep<=this.gradosServo254((float)clSiguienteClave.servo3,3);angleSteep++){
							oStream.write((byte)255);
							oStream.write((byte)3);
							oStream.write((byte)angleSteep);
							
			//				System.out.println(angleSteep);
						}
					}else{
						for(int angleSteep=this.gradosServo254((float)clActual.servo3,3);angleSteep>=this.gradosServo254((float)clSiguienteClave.servo3,3);angleSteep--){
							oStream.write((byte)255);
							oStream.write((byte)3);
							oStream.write((byte)angleSteep);
							
			//				System.out.println(angleSteep);
						}
					}					

					if (this.gradosServo254((float)clActual.servo4,4)<this.gradosServo254((float)clSiguienteClave.servo4,4)){
						for(int angleSteep=this.gradosServo254((float)clActual.servo4,4);angleSteep<=this.gradosServo254((float)clSiguienteClave.servo4,4);angleSteep++){
							oStream.write((byte)255);
							oStream.write((byte)4);
							oStream.write((byte)angleSteep);


						}
					}else{
						for(int angleSteep=this.gradosServo254((float)clActual.servo4,4);angleSteep>=this.gradosServo254((float)clSiguienteClave.servo4,4);angleSteep--){
							oStream.write((byte)255);
							oStream.write((byte)4);
							oStream.write((byte)angleSteep);


						}
					}	

					

	
					if(clSiguienteClave.tipo.compareTo("FINAL")==0){
						l=this.mapClaves.size();
					}
				}
				
				
				puertoSerial.close();
				System.out.println("Transmisión por el puerto:" + this.idPuerto + " realizada correctamente");
				
				
			}

			
			
		}catch(Exception e)
		{
			
			System.out.println("Error transmitiendo por puerto serial:" + e.getMessage());
		}finally{
			this.transmitirPuerto=false;
		}		
		
	}

	
	/**
	 * Convierte los grados en valores de grados para el servomotor. 180 grados equivalen
	 * a 254 en valores del servo. 
	 * @param gradosServo360
	 * @return
	 */
	int gradosServo254(float gradosServo180,int idServo){
		int gServo254=0; 
		switch(idServo){
		case 2:
			gServo254 =  Math.round((gradosServo180/90)*254)+20;
			break;
		case 3:
			gServo254 = (-1*Math.round((gradosServo180/90)*254)+127) + 20;
			break;
		case 4:
			//gServo254 =  Math.round((gradosServo180/90)*254)+63;
			//System.out.println(gradosServo180 + "-" + gServo254);
			//gServo254 = -1*Math.round((gradosServo180/90)*254)+127;
			gServo254 =  Math.round((gradosServo180/90)*254);
			//System.out.println(gradosServo180 + " * " + gServo254);
			gServo254 = 254;
			/**
			?  = (gradoServo180 * 255) + 45
				       -----------
					  90°
			**/
			
			gServo254 = Math.round(((gradosServo180 + 45) * 255)/90);	  
			
			break;
		default:
			gServo254 = -1*Math.round((gradosServo180/90)*254)+127;
			break;
		}
			
			
		
		
		
		
		
		return(gServo254) ;
	}
	
	/**
	 * Carga una imagen en memoria
	 * @param path
	 * @return
	 */

	 private int[] loadTexture(String path) {
	        IntBuffer image = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
	        IL.ilGenImages(image);
	        IL.ilBindImage(image.get(0));
	        IL.ilLoadImage(path);
	        IL.ilConvertImage(IL.IL_RGB, IL.IL_BYTE);
	        ByteBuffer scratch = ByteBuffer.allocateDirect(IL.ilGetInteger(IL.IL_IMAGE_WIDTH) * IL.ilGetInteger(IL.IL_IMAGE_HEIGHT) * 3);
	        IL.ilCopyPixels(0, 0, 0, IL.ilGetInteger(IL.IL_IMAGE_WIDTH), IL.ilGetInteger(IL.IL_IMAGE_HEIGHT), 1, IL.IL_RGB, IL.IL_BYTE, scratch);
	
	        // Create A IntBuffer For Image Address In Memory
	        IntBuffer buf = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder()).asIntBuffer();
	        GL11.glGenTextures(buf); // Create Texture In OpenGL
	
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0));
	        // Typical Texture Generation Using Data From The Image
	
	        // Create Nearest Filtered Texture
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0));
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, IL.ilGetInteger(IL.IL_IMAGE_WIDTH), 
	                IL.ilGetInteger(IL.IL_IMAGE_HEIGHT), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, scratch);
	
	        // Create Linear Filtered Texture
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(1));
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, IL.ilGetInteger(IL.IL_IMAGE_WIDTH), 
	                IL.ilGetInteger(IL.IL_IMAGE_HEIGHT), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, scratch);
	
	        // Create MipMapped Texture
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(2));
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
	        GLU.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, 3, IL.ilGetInteger(IL.IL_IMAGE_WIDTH), 
	                IL.ilGetInteger(IL.IL_IMAGE_HEIGHT), GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, scratch);
	
	      return new int[]{ buf.get(0), buf.get(1), buf.get(2) };     // Return Image Addresses In Memory
	    }

	 /**
	  * Carga las imagenes de los botones en memoria
	  *
	  */
	    private void loadTextures() {
	    	this.btnRotarY = loadTexture("imagenes/btnRotarY.bmp");
	    	this.btnMoverY = loadTexture("imagenes/btnMoverY.bmp");
	    	this.btnAcercar = loadTexture("imagenes/btnAcercar.bmp");
	    	this.btnServo0 = loadTexture("imagenes/btnServo0.bmp");
	    	this.btnServo1 = loadTexture("imagenes/btnServo1.bmp");
	    	this.btnServo2 = loadTexture("imagenes/btnServo2.bmp");
	    	this.btnServo3 = loadTexture("imagenes/btnServo3.bmp");
	    	this.btnServo4 = loadTexture("imagenes/btnServo4.bmp");
	    	this.btnTomarObjeto = loadTexture("imagenes/btnTomarObjeto.bmp");
	    	this.btnTransmisionSerial = loadTexture("imagenes/btnTransmisionSerial.bmp");
	    	this.btnSiguienteCuadro = loadTexture("imagenes/btnSiguienteCuadro.bmp");
	    	this.btnLiberarObjeto = loadTexture("imagenes/btnLiberarObjeto.bmp");
	    	this.btnGuardarXml = loadTexture("imagenes/btnGuardarXml.bmp");
	    	this.btnEjecutarMovimiento = loadTexture("imagenes/btnEjecutarMovimiento.bmp");
	    	this.btnCuadro1 = loadTexture("imagenes/btnCuadro1.bmp");
	    	
	    	this.btnGuardarClave = loadTexture("imagenes/btnGuardarClave.bmp");
	    	this.btnPegarClave = loadTexture("imagenes/btnPegarClave.bmp");
	    	this.btnCopiarClave = loadTexture("imagenes/btnCopiarClave.bmp");



	    }
	    
        /**
         * Copiar la posición de los servos en la clave actual
         */
	    private void copiarClaveActual(){
	    	this.idClaveCopiada = this.claveActual.id;
	    }
	    
	    /**
         * Asignar los movimientos copiados a la clave actual
         */
	    
	    private void pegarClaveActual(){
    	
	    	Clave tmpClave = (Clave)this.mapClaves.get(Integer.toString(this.idClaveCopiada));
	    	this.claveActual.servo0 = tmpClave.servo0;
	    	this.claveActual.servo1 = tmpClave.servo1;
	    	this.claveActual.servo2 = tmpClave.servo2;
	    	this.claveActual.servo3 = tmpClave.servo3;
	    	this.claveActual.servo4 = tmpClave.servo4;
	    	
	    	this.angleBaseHexagonal = tmpClave.servo0;
	    	this.angleAnteBrazo = tmpClave.servo1;
	    	this.angleBrazo = tmpClave.servo2;
	    	this.angleMano = tmpClave.servo3;
	    	this.angleDedo1 = tmpClave.servo4;
	    	
	    	this.mapClaves.put(this.claveActual,Integer.toString(this.idClaveCopiada));
	    	
	    	//tmpClave.id = this.claveActual.id;
	    	
	    	
	    	
	    }

}
/**Definición de la clase material la cual determina el
 * color de los objetos
 */
class Material{
	/**
	 * Nombre del material
	 */
	public String nombre;
	/**
	 * Componente roja del material
	 */
	public float difuseR;
	/**
	 * Componente verde del material
	 */
	public float difuseG;
	/**
	 * Componente azul del material
	 */
	public float difuseB;

}
/**Definición de la clase vertice, con la información de las
 * coordenadas de estos en los tres ejes.
 */
class Vertice{
	/**
	 * Posición x de el vertice
	 */
	public float x;
	/**
	 * Posición y de el vertice
	 */
	public float y;
	/**
	 * Posición z de el vertice
	 */
	public float z;
}
/**Información de las caras las cuales estan compuestas por
 * 3 o mas vertices.
 */
class Cara{
	/**
	 * Nombre del material de la cara
	 */
	public String material;
	/**
	 * Grupo al cual pertenece la cara
	 */
	public String grupo;
	/**
	 * Cantidad de vertices que conforman la cara
	 */
	public int numeroVertices;
	/**
	 * Arreglo con el id de cada vertice que conforma la cara
	 */
	public int[] idVertice = new int[20];

}
/**Definición de los grupos a los cuales pertenecen las diferentes
 * caras.
 */
class Grupo{
	/**
	 * Nombre del grupo
	 */
	public String nombre;
	/**
	 * Identificador único del grupo
	 */
	public int idGrupo;
}
/**Cuadro clave con la infroamción de cada uno de los servos
 * en una posición
 */
class Clave{
	/**
	 * Identificador único de la clave
	 */
	public int id;
	/**
	 * Puede ser INICIO|INTERMEDIO|FINAL
	 */
	public String tipo;
	public int siguienteClave;
	/**
	 * Rotación del servo 0 en la clave actual
	 */
	public float servo0;
	/**
	 * Rotación del servo 1 en la clave actual
	 */
	public float servo1;
	/**
	 * Rotación del servo 2 en la clave actual
	 */
	public float servo2;
	/**
	 * Rotación del servo 3 en la clave actual
	 */
	public float servo3;
	/**
	 * Rotación del servo 4 en la clave actual
	 */
	public float servo4;
	/**
	 * Indica si el objeto se toma. 0 suelto, 1 tomado
	 */
	public int objetoTomado;
}



