package com.ping.dashboardv2;

import java.util.Calendar;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;


public class MainActivity extends Activity {

	/*Cr�ation des variables*/
	private RelativeLayout clignoGauche;
	private RelativeLayout clignoDroit;
	private RelativeLayout smiley; 			//smiley de conso
	private RelativeLayout huile;			//niveau d'huile
	private RelativeLayout tempLiquide;		//temperature liquide de refroidissement
	private RelativeLayout tempHuile;		//temperature huile moteur
	private RelativeLayout batterie;		//voyant batterie moteur
	private RelativeLayout feuxRoute;
	private RelativeLayout feuxCroisement;
	private TextView vitesses;				//vitesses pass�es par le motard
	private TextView pointMort;				//voyant point mort
	private TextView heures;				
	private TextView minutes;
	private TextView deuxPoints;			//s�parateur heures minutes
	private TextView joursLettres;
	private TextView jours;
	private TextView mois;
	private TextView slash;					//s�parateur jours mois
	private ImageView aiguilleVitesse;
	private ImageView aiguille2;
	private TextView niveauBatterie;
	private RelativeLayout rlAiguille;
	private float angleVitesse;
	
	private boolean id_cligno;
	private int nb_clic_gauche;
	private int nb_clic_droit;
	private int compteur_cligno;
	private int nb_clic_feux_croisement;
	private int nb_clic_feux_route;
	private int nb_clic_smiley;
	private int nb_clic_batterie;
	private int nb_clic_huile;
	private int nb_clic_tempHuile;
	private int nb_clic_tempLiquide;
	
	/*For tests only*/
	private EditText etVitesse;
	private float fltVitesse;
	
	/*Fonctions*/
	private Handler handler = new Handler();
	private Runnable start;
	private Runnable setup;
	private Runnable clignoter;
	private Runnable attribuer_jour;
	
	
	/***********************
	 * Gestion date / heure*
	 ***********************/
	Calendar calendrier;
	private int intMinutes;
	private int intHeures;
	private int intJour_nb;
	private int intJour_nom;
	private int intMois;
	char nom_du_jour;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*Forcer l'application en mode paysage*/
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		
		//font sp�ciale
		Typeface font = Typeface.createFromAsset(getAssets(), "ds_digi.ttf");
		
		/*Initialisation des �l�ments graphiques*/
		clignoGauche = (RelativeLayout)findViewById(R.id.rlClignoGauche);
		clignoDroit = (RelativeLayout)findViewById(R.id.rlClignoDroit);
		smiley = (RelativeLayout)findViewById(R.id.rlSmiley);
		
		batterie = (RelativeLayout)findViewById(R.id.rlBatterie);
		tempHuile = (RelativeLayout)findViewById(R.id.rlTempHuile);
		tempLiquide = (RelativeLayout)findViewById(R.id.rlTempLiquide);
		huile = (RelativeLayout)findViewById(R.id.rlHuile);
		
		feuxCroisement = (RelativeLayout)findViewById(R.id.croisement);
		feuxRoute = (RelativeLayout)findViewById(R.id.phares);
		
		vitesses = (TextView)findViewById(R.id.tvVitesses);
		pointMort = (TextView)findViewById(R.id.tvPointMort);
		
		heures = (TextView)findViewById(R.id.tvHeures);
		minutes = (TextView)findViewById(R.id.tvMinutes);
		deuxPoints = (TextView)findViewById(R.id.tvDeuxPoints);
		
		joursLettres = (TextView)findViewById(R.id.jourLettres);
		jours = (TextView)findViewById(R.id.jour);
		mois = (TextView)findViewById(R.id.mois);
		slash = (TextView)findViewById(R.id.slash);
		
		aiguilleVitesse = (ImageView)findViewById(R.id.aiguilleVitesse);
		
		niveauBatterie = (TextView)findViewById(R.id.tvNivBatterie);
		
		rlAiguille = (RelativeLayout)findViewById(R.id.rlAiguille);
		aiguille2 = (ImageView)findViewById(R.id.aiguilleTour);
		
		/*Test*/
		etVitesse = (EditText)findViewById(R.id.editTextVitesse);
		
		vitesses.setTypeface(font);
		pointMort.setTypeface(font);
		heures.setTypeface(font);
		minutes.setTypeface(font);
		deuxPoints.setTypeface(font);
		joursLettres.setTypeface(font);
		jours.setTypeface(font);
		mois.setTypeface(font);
		slash.setTypeface(font);
		niveauBatterie.setTypeface(font);
		
		attribuer_jour = new Runnable(){
			@Override
			public void run(){
				
				switch(intJour_nom){
				case Calendar.MONDAY:
					joursLettres.setText("LUN");
					break;	
					
				case Calendar.TUESDAY:
					joursLettres.setText("MAR");
					break;
					
				case Calendar.WEDNESDAY:
					joursLettres.setText("MER");
					break;
					
				case Calendar.THURSDAY:
					joursLettres.setText("JEU");
					break;
					
				case Calendar.FRIDAY:
					joursLettres.setText("VEN");
					break;
					
				case Calendar.SATURDAY:
					joursLettres.setText("SAM");
					break;
					
				case Calendar.SUNDAY:
					joursLettres.setText("DIM");
					break;
					
				default:
					joursLettres.setText("ERR");
					break;
				}
				
				
			}
		};

		//Proc�dure d'initialisation
		start = new Runnable(){
			@Override
			public void run(){
				clignoGauche.setBackgroundResource(R.color.green);
				clignoDroit.setBackgroundResource(R.color.green);
				smiley.setBackgroundResource(R.color.green);
				batterie.setBackgroundResource(R.color.orange);
				huile.setBackgroundResource(R.color.orange);
				tempLiquide.setBackgroundResource(R.color.orange);
				tempHuile.setBackgroundResource(R.color.orange);
				feuxRoute.setBackgroundResource(R.color.blue);
				feuxCroisement.setBackgroundResource(R.color.green);
				
				//Font
				vitesses.setTextColor(getResources().getColor(R.color.blue));
				pointMort.setTextColor(getResources().getColor(R.color.blue));
				heures.setTextColor(getResources().getColor(R.color.blue));
				minutes.setTextColor(getResources().getColor(R.color.blue));
				deuxPoints.setTextColor(getResources().getColor(R.color.blue));
				joursLettres.setTextColor(getResources().getColor(R.color.blue));
				jours.setTextColor(getResources().getColor(R.color.blue));
				mois.setTextColor(getResources().getColor(R.color.blue));
				slash.setTextColor(getResources().getColor(R.color.blue));
				
				niveauBatterie.setTextColor(getResources().getColor(R.color.blue));

				handler.postDelayed(setup, 2000);
			}
		};
		
		
		/*Setup retard� de 2 secondes pour que tous les composants s'allument*/
		setup = new Runnable(){
			@Override
			public void run(){
				clignoGauche.setBackgroundResource(R.color.bck_color);
				clignoDroit.setBackgroundResource(R.color.bck_color);
				smiley.setBackgroundResource(R.color.bck_color);
				batterie.setBackgroundResource(R.color.bck_color);
				huile.setBackgroundResource(R.color.bck_color);
				tempLiquide.setBackgroundResource(R.color.bck_color);
				tempHuile.setBackgroundResource(R.color.bck_color);
				feuxRoute.setBackgroundResource(R.color.bck_color);
				feuxCroisement.setBackgroundResource(R.color.bck_color);
				fltVitesse = 0.0f;
				angleVitesse = 45 + 135*fltVitesse/90;
				rlAiguille.setRotation(angleVitesse);
				
				
				id_cligno = false;
				nb_clic_gauche = 0;
				nb_clic_droit = 0;
				nb_clic_smiley = 0;
				compteur_cligno = 0;
				nb_clic_feux_croisement = 0;
				nb_clic_feux_route = 0;
				nb_clic_batterie = 0;
				nb_clic_huile = 0;
				nb_clic_tempHuile = 0;
				nb_clic_tempLiquide = 0;
			}
		};
		
		handler.post(start);
		
		/*Mise � jour de l'heure et la date en temps r�el*/
		Thread t = new Thread(){
			@Override
			public void run(){
				try{
					while (!isInterrupted()){
						Thread.sleep(1000);
						runOnUiThread(new Runnable(){
							@Override
							public void run(){
								
								calendrier = Calendar.getInstance();
								intMinutes = calendrier.get(Calendar.MINUTE);
								intHeures = calendrier.get(Calendar.HOUR_OF_DAY);
								intJour_nb = calendrier.get(Calendar.DAY_OF_MONTH);
								intJour_nom = calendrier.get(Calendar.DAY_OF_WEEK);
								intMois = calendrier.get(Calendar.MONTH) + 1;
								
								heures.setText(Integer.toString(intHeures));
								deuxPoints.setText(":");
								slash.setText("/");
								minutes.setText(Integer.toString(intMinutes));
								attribuer_jour.run();
								jours.setText(Integer.toString(intJour_nb));
								mois.setText(Integer.toString(intMois));
							}
						});
					}
				}catch (InterruptedException e){
					
				}
			}
		};
		
		t.start();
		
		/*Fonction permettant au clignotant de se mettre en marche*/
		clignoter = new Runnable(){
			@Override
			public void run(){
				
				if (id_cligno == false){
					if (compteur_cligno%2 == 0){
						clignoGauche.setBackgroundResource(R.color.green);
					}else{
						clignoGauche.setBackgroundResource(R.color.bck_color);
					}
				}else{				
					if (compteur_cligno%2 == 0){
						clignoDroit.setBackgroundResource(R.color.green);
					}else{
						clignoDroit.setBackgroundResource(R.color.bck_color);
					}
				}
				
				handler.postDelayed(clignoter, 400);
				compteur_cligno++;
			}
		};
		
	}
	
	public class RunnableParam implements Runnable{
		
		private int i;

		public RunnableParam(int _i){
			this.i = _i;
		}
		
		@Override
		public void run(){
			aiguilleVitesse.setRotation(i);
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
    }
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) { 
		
			case R.id.itemClignoGauche:
				id_cligno = false;
				handler.removeCallbacks(clignoter);
				clignoDroit.setBackgroundResource(R.color.bck_color);
				
				if (nb_clic_droit%2==1){
					nb_clic_droit++;
				}
				
				if (nb_clic_gauche%2 == 0){
					handler.post(clignoter);

				}else{
					clignoGauche.setBackgroundResource(R.color.bck_color);
					handler.removeCallbacks(clignoter);
				}
				nb_clic_gauche++;
				return true;
			
			
			case R.id.itemClignoDroit:
				id_cligno = true;
				handler.removeCallbacks(clignoter);
				clignoGauche.setBackgroundResource(R.color.bck_color);
				
				if (nb_clic_gauche%2==1){
					nb_clic_gauche++;
				}
				
				if (nb_clic_droit%2 == 0){
					handler.post(clignoter);

				}else{
					clignoDroit.setBackgroundResource(R.color.bck_color);
					handler.removeCallbacks(clignoter);
				}
				
				nb_clic_droit++;	
				return true;
			
			case R.id.itemSmiley:
				if (nb_clic_smiley%2 == 0){
					smiley.setBackgroundResource(R.color.green);
				}
				else{
					smiley.setBackgroundResource(R.color.bck_color);
				}
				nb_clic_smiley++;	
				return true;
			
			case R.id.itemVitesses:
				
				aiguilleVitesse.setPivotX((rlAiguille.getWidth())/2);
				aiguilleVitesse.setPivotY((rlAiguille.getHeight())/2);
				
				
				fltVitesse = Float.valueOf(etVitesse.getText().toString());
				angleVitesse = 45 + 135*fltVitesse/90;
						
				rlAiguille.setRotation(angleVitesse);
				
				aiguille2.setPivotX(aiguille2.getWidth());
				aiguille2.setPivotY(aiguille2.getHeight());
				
				aiguille2.setRotation(fltVitesse);
				
				
				/*int i;
				
				for (i=0; i<intVitesse; i++){
					bouger_aiguille = new RunnableParam(i);
					handler.postDelayed(bouger_aiguille, 50);
				}*/
				
				
				return true;
			
			case R.id.itemCroisement:
				if (nb_clic_feux_croisement%2 == 0){
					feuxCroisement.setBackgroundResource(R.color.green);
				}
				else{
					feuxCroisement.setBackgroundResource(R.color.bck_color);
				}
				nb_clic_feux_croisement++;	
				return true;
			
			case R.id.itemRoute:
				if (nb_clic_feux_route%2 == 0){
					feuxRoute.setBackgroundResource(R.color.blue);
				}
				else{
					feuxRoute.setBackgroundResource(R.color.bck_color);
				}
				nb_clic_feux_route++;
				return true;
		
			case R.id.itemBatterie:
				if (nb_clic_batterie%2 == 0){
					batterie.setBackgroundResource(R.color.orange);
				}
				else{
					batterie.setBackgroundResource(R.color.bck_color);
				}
				nb_clic_batterie++;
				return true;
		
			case R.id.itemHuile:
				if (nb_clic_huile%2 == 0){
					huile.setBackgroundResource(R.color.orange);
				}
				else{
					huile.setBackgroundResource(R.color.bck_color);
				}
				nb_clic_huile++;
				return true;
				
			case R.id.itemTempHuile:
				if (nb_clic_tempHuile%2 == 0){
					tempHuile.setBackgroundResource(R.color.orange);
				}
				else{
					tempHuile.setBackgroundResource(R.color.bck_color);
				}
				nb_clic_tempHuile++;
				return true;
				
			case R.id.itemTempLiquide:
				if (nb_clic_tempLiquide%2 == 0){
					tempLiquide.setBackgroundResource(R.color.orange);
				}
				else{
					tempLiquide.setBackgroundResource(R.color.bck_color);
				}
				nb_clic_tempLiquide++;
				return true;
		}
		
		return false;
	}
}