package com.ping.dashboardv2;

import java.util.Calendar;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.*;



//
public class MainActivity extends Activity {

	
	/*Création des variables*/
	private RelativeLayout 	clignoGauche;
	private RelativeLayout 	clignoDroit;
	private RelativeLayout 	smiley; 			//smiley de conso
	private RelativeLayout 	huile;				//niveau d'huile
	private RelativeLayout 	tempLiquide;		//temperature liquide de refroidissement
	private RelativeLayout 	tempHuile;			//temperature huile moteur
	private RelativeLayout 	batterie;			//voyant batterie moteur
	private RelativeLayout 	feuxRoute;
	private RelativeLayout 	feuxCroisement;
	private TextView 		vitesses;			//vitesses passées par le motard
	private TextView 		pointMort;			//voyant point mort
	private TextView 		heures;				
	private TextView 		minutes;
	private TextView 		deuxPoints;			//séparateur heures minutes
	private TextView 		joursLettres;
	private TextView 		jours;
	private TextView 		mois;
	private TextView 		slash;				//séparateur jours mois
	private RelativeLayout 	rlAiguilleTours;
	private TextView 		niveauBatterie;
	private RelativeLayout 	rlAiguille;
	
	private RelativeLayout 	essence1;			//Voyant essence écran 1
	private RelativeLayout 	essence2;
	private RelativeLayout 	essence3;
	private RelativeLayout	essence4;
	private RelativeLayout 	essence5;
	
	/*Ecran 2*/
	private TextView 		kmDepuisPlein;
	private TextView		tvKm;
	private TextView 		litresAuCent;
	private TextView		tvLitres;
	
	/*Ecran 4*/
	private TextView		kilometrage;
	private TextView		tvKilometrage;
	private TextView 		kmLAC; 				//Litres au cent
	private TextView		tvKmLAC;
	private TextView		kmLACAVG; 			//average
	private TextView		tvKmLACAVG;
	
	/*Ecran 5*/
	private TextView 		kmLAC5;
	private TextView		tvKmLAC5;
	private TextView 		kmToPompe;
	private TextView		tvKmToPompe;
	
	private RelativeLayout 	ecran1;
	private RelativeLayout 	ecran2;
	private RelativeLayout	ecran3;
	private RelativeLayout 	ecran4;
	private RelativeLayout 	ecran5;
	
	private Button 	boutonA;
	private int nb_clic_boutonA;
	
	private Button boutonB;
	
	private int cligno_heure;
	private int cligno_minute;
	private int cligno_jour_lettres;
	private boolean longClickIsActivated;
	private boolean BIsClicked;
	private int nb_appels_bouton_A;
	private int date;					//1: heures /2: minutes /3: nom jour /4: jour /5: mois
	private int cligno_jours;
	private int cligno_mois;
	
	private boolean id_cligno;
	private int nb_clic_gauche;
	private int nb_clic_droit;
	private int compteur_cligno;
	private int compteur_batterie;
	private int nb_clic_feux_croisement;
	private int nb_clic_feux_route;
	private int nb_clic_smiley;
	private int nb_clic_point_mort;
	private int nb_clic_batterie;
	private int nb_clic_huile;
	private int nb_clic_tempHuile;
	private int nb_clic_tempLiquide;
	private int niveau_batterie;
	private int niveau_essence;
	private int nb_clic_essence;
	
	private Button batterieNiveau1;
	private Button batterieNiveau2;
	private Button batterieNiveau3;
	private Button batterieNiveau4;
	
	private Button essenceNiveau1;
	private Button essenceNiveau2;
	private Button essenceNiveau3;
	private Button essenceNiveau4;
	private Button essenceNiveau5;
	private Button essenceNiveau6;
	
	/*For tests only*/
	private EditText etVitesse;
	private EditText etBoiteVit;
	private float fltVitesse;
	private float fltTours;
	private float angleVitesse;
	private float angleTours;
	
	/*Navigation bar
	private DrawerLayout 	drawerLayout;
	private ListView 		listView;*/
	
	/*Fonctions*/
	private Handler handler = new Handler();
	private Runnable start;
	private Runnable setup;
	private Runnable clignoter;
	private Runnable heure_clignoter;
	private Runnable minute_clignoter;
	private Runnable nom_jour_clignoter;
	private Runnable jour_clignoter;
	private Runnable batterie_clignoter;
	private Runnable attribuer_jour;
	private Runnable mois_clignoter;
	
	
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
		/*Forcer l'écran à rester allumé*/
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//font spéciale
		Typeface font = Typeface.createFromAsset(getAssets(), "ds_digi.ttf");
		
		/*Initialisation des éléments graphiques*/
		clignoGauche = (RelativeLayout)findViewById(R.id.rlClignoGauche);
		clignoDroit = (RelativeLayout)findViewById(R.id.rlClignoDroit);
		smiley = (RelativeLayout)findViewById(R.id.rlSmiley);
		
		batterie = (RelativeLayout)findViewById(R.id.rlBatterie);
		tempHuile = (RelativeLayout)findViewById(R.id.rlTempHuile);
		tempLiquide = (RelativeLayout)findViewById(R.id.rlTempLiquide);
		huile = (RelativeLayout)findViewById(R.id.rlHuile);
		
		essence1 = (RelativeLayout)findViewById(R.id.rlEssence1);
		essence2 = (RelativeLayout)findViewById(R.id.rlEssence2);
		essence3 = (RelativeLayout)findViewById(R.id.rlEssence3);
		essence4 = (RelativeLayout)findViewById(R.id.rlEssence4);
		essence5 = (RelativeLayout)findViewById(R.id.rlEssence5);
		
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
		
		niveauBatterie = (TextView)findViewById(R.id.tvNivBatterie);
		
		rlAiguille = (RelativeLayout)findViewById(R.id.rlAiguille);
		rlAiguilleTours = (RelativeLayout)findViewById(R.id.rlAiguilleTours);
		
		batterieNiveau1 = (Button)findViewById(R.id.niv1); 					//1 - 24% + cligno entre 1 - 5%
		batterieNiveau2 = (Button)findViewById(R.id.niv2);					//25 - 49%
		batterieNiveau3 = (Button)findViewById(R.id.niv3);					//50 - 74%
		batterieNiveau4 = (Button)findViewById(R.id.niv4);					//75 - 100%
		
		essenceNiveau1 = (Button)findViewById(R.id.essenceNiv1);
		essenceNiveau2 = (Button)findViewById(R.id.essenceNiv2);
		essenceNiveau3 = (Button)findViewById(R.id.essenceNiv3);
		essenceNiveau4 = (Button)findViewById(R.id.essenceNiv4);
		essenceNiveau5 = (Button)findViewById(R.id.essenceNiv5);
		essenceNiveau6 = (Button)findViewById(R.id.essenceNiv6);
		
		ecran1 = (RelativeLayout)findViewById(R.id.ecran1);
		ecran2 = (RelativeLayout)findViewById(R.id.ecran2);
		ecran3 = (RelativeLayout)findViewById(R.id.ecran3);
		ecran4 = (RelativeLayout)findViewById(R.id.ecran4);
		ecran5 = (RelativeLayout)findViewById(R.id.ecran5);
		
		/*Ecran 2*/
		kmDepuisPlein = (TextView)findViewById(R.id.tvKmDepuisPlein);
		tvKm = (TextView)findViewById(R.id.km);
		litresAuCent = (TextView)findViewById(R.id.litresAuCent);
		tvLitres = (TextView)findViewById(R.id.tvLitres);
		
		/*Ecran4*/
		kilometrage = (TextView)findViewById(R.id.kilometrage);
		tvKilometrage = (TextView)findViewById(R.id.tvKmetrage);
		kmLAC = (TextView)findViewById(R.id.litresAuCent4);
		tvKmLAC = (TextView)findViewById(R.id.tvLAC);
		kmLACAVG = (TextView)findViewById(R.id.LACAVG);
		tvKmLACAVG = (TextView)findViewById(R.id.tvLACAvg);
		
		/*Ecran 5*/
		kmLAC5 = (TextView)findViewById(R.id.LAC5);
		tvKmLAC5 = (TextView)findViewById(R.id.tvLAC5);
		kmToPompe = (TextView)findViewById(R.id.kmToPompe);
		tvKmToPompe = (TextView)findViewById(R.id.tvKmToPompe);
		
		boutonA = (Button)findViewById(R.id.btnA);
		boutonB = (Button)findViewById(R.id.btnB);
		
		/*Test*/
		etVitesse = (EditText)findViewById(R.id.editTextVitesse);
		etBoiteVit = (EditText)findViewById(R.id.editTextBoiteVit);
		
		/*Navigation Bar
		drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		listView = (ListView)findViewById(R.id.left_drawer);*/
		
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
		
		/*Ecran 2*/
		kmDepuisPlein.setTypeface(font);
		tvKm.setTypeface(font);
		litresAuCent.setTypeface(font);
		tvLitres.setTypeface(font);
		
		/*Ecran 4*/
		kilometrage.setTypeface(font);
		tvKilometrage.setTypeface(font);
		kmLAC.setTypeface(font);
		tvKmLAC.setTypeface(font);
		kmLACAVG.setTypeface(font);
		tvKmLACAVG.setTypeface(font);
		
		/*Ecran 5*/
		kmLAC5.setTypeface(font);
		tvKmLAC5.setTypeface(font);
		kmToPompe.setTypeface(font);
		tvKmToPompe.setTypeface(font);

		
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
					joursLettres.setText(" ");
					break;
				}	
			}
		};

		//Procédure d'initialisation
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
				pointMort.setTextColor(getResources().getColor(R.color.green));
				heures.setTextColor(getResources().getColor(R.color.blue));
				minutes.setTextColor(getResources().getColor(R.color.blue));
				deuxPoints.setTextColor(getResources().getColor(R.color.blue));
				joursLettres.setTextColor(getResources().getColor(R.color.blue));
				jours.setTextColor(getResources().getColor(R.color.blue));
				mois.setTextColor(getResources().getColor(R.color.blue));
				slash.setTextColor(getResources().getColor(R.color.blue));
				
				
				/*Ecran 2*/
				kmDepuisPlein.setTextColor(getResources().getColor(R.color.blue));
				tvKm.setTextColor(getResources().getColor(R.color.blue));
				litresAuCent.setTextColor(getResources().getColor(R.color.blue));
				tvLitres.setTextColor(getResources().getColor(R.color.blue));
				
				/*Ecran 4*/
				kilometrage.setTextColor(getResources().getColor(R.color.blue));
				tvKilometrage.setTextColor(getResources().getColor(R.color.blue));
				kmLAC.setTextColor(getResources().getColor(R.color.blue));
				tvKmLAC.setTextColor(getResources().getColor(R.color.blue));
				kmLACAVG.setTextColor(getResources().getColor(R.color.blue));
				tvKmLACAVG.setTextColor(getResources().getColor(R.color.blue));
				
				/*Ecran 5*/
				kmLAC5.setTextColor(getResources().getColor(R.color.blue));
				tvKmLAC5.setTextColor(getResources().getColor(R.color.blue));
				kmToPompe.setTextColor(getResources().getColor(R.color.blue));
				tvKmToPompe.setTextColor(getResources().getColor(R.color.blue));
				
				niveauBatterie.setTextColor(getResources().getColor(R.color.blue));
				
				ecran1.setVisibility(View.VISIBLE);
				ecran2.setVisibility(View.GONE);
				ecran3.setVisibility(View.GONE);
				ecran4.setVisibility(View.GONE);
				ecran5.setVisibility(View.GONE);

				handler.postDelayed(setup, 2000);
			}
		};
		
		
		/*Setup retardé de 2 secondes pour que tous les composants s'allument*/
		setup = new Runnable(){
			@Override
			public void run(){
				clignoGauche.setBackgroundResource(R.color.bck_color);
				clignoDroit.setBackgroundResource(R.color.bck_color);
				smiley.setBackgroundResource(R.color.bck_color);
				vitesses.setVisibility(View.INVISIBLE);
				pointMort.setVisibility(View.VISIBLE);
				batterie.setBackgroundResource(R.color.bck_color);
				huile.setBackgroundResource(R.color.bck_color);
				tempLiquide.setBackgroundResource(R.color.bck_color);
				tempHuile.setBackgroundResource(R.color.bck_color);
				feuxRoute.setBackgroundResource(R.color.bck_color);
				feuxCroisement.setBackgroundResource(R.color.bck_color);

				essence1.setBackgroundResource(R.color.bck_color);
				essence2.setBackgroundResource(R.color.bck_color);
				essence3.setBackgroundResource(R.color.bck_color);
				essence4.setBackgroundResource(R.color.bck_color);
				essence5.setBackgroundResource(R.color.bck_color);
				
				/*Initialisation de la date*/
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
				
				
				//met l'angle d'origine de la vitesse en Km à 0
				fltVitesse = 0.0f;
				angleVitesse = 45 + 135*fltVitesse/90;
				rlAiguille.setRotation(angleVitesse);
				//met l'angle d'origine de la vitesse moteur en tr/min à 0
				fltTours = 0.0f;
				angleTours = 24 + 89 * fltTours / 3;
				rlAiguilleTours.setRotation(angleTours);
				
				id_cligno = false;
				nb_clic_gauche = 0;
				nb_clic_droit = 0;
				nb_clic_smiley = 0;
				nb_clic_point_mort = 0;
				compteur_cligno = 0;
				compteur_batterie = 0;
				nb_clic_feux_croisement = 0;
				nb_clic_feux_route = 0;
				nb_clic_batterie = 0;
				nb_clic_huile = 0;
				nb_clic_tempHuile = 0;
				nb_clic_tempLiquide = 0;
				niveau_batterie = 0;			//affichage du niveau de batterie
				nb_clic_essence = 0;
				nb_clic_boutonA = 0;
				nb_appels_bouton_A = 0;
				
				cligno_heure = 0;
				cligno_minute = 0;
				longClickIsActivated = false;
				BIsClicked = false;
				date = 5;
				cligno_jour_lettres = 0;
				cligno_mois=0;
				cligno_jours=0;
			}
		};
		
		handler.post(start);
		
		/*Mise à jour de l'heure et la date en temps réel
		Thread t = new Thread(){
			@Override
			public void run(){
				try{
					while (!isInterrupted()){
						Thread.sleep(1000);
						runOnUiThread(new Runnable(){
							@Override
							public void run(){
								
								if (cligno_heure == 0){
								
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
							}
						});
					}
				}catch (InterruptedException e){
					
				}
			}
		};
		
		t.start();*/
		
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
		
		/*Fonction permettant à l'heure de clignoter apres appui long sur A*/
		heure_clignoter = new Runnable(){
			@Override
			public void run(){

				date = 0;
				
				mois.setVisibility(View.VISIBLE);
				handler.removeCallbacks(mois_clignoter);
				
				if (cligno_heure%2 == 0){
					heures.setVisibility(View.INVISIBLE);
				}
				else{
					heures.setVisibility(View.VISIBLE);
				}
				
				if (BIsClicked == true){
					intHeures = Integer.parseInt(heures.getText().toString());
					heures.setText(String.valueOf((intHeures+1)%24));
					
					BIsClicked = false;
				}
				
				handler.postDelayed(heure_clignoter, 400);
				cligno_heure++;
			}
		};
		
		minute_clignoter = new Runnable(){
			@Override
			public void run(){
				
				date = 1;
				
				heures.setVisibility(View.VISIBLE);
				handler.removeCallbacks(heure_clignoter);
				
				if (cligno_minute%2 == 0){
					minutes.setVisibility(View.INVISIBLE);
				}
				else{
					minutes.setVisibility(View.VISIBLE);
				}
				
				if (BIsClicked == true){
					intMinutes = Integer.parseInt(minutes.getText().toString());
					minutes.setText(String.valueOf((intMinutes+1)%60));
					
					BIsClicked = false;
				}
				
				handler.postDelayed(minute_clignoter, 400);
				cligno_minute++;
			}
		};
		
		nom_jour_clignoter = new Runnable(){
			@Override
			public void run(){
				date = 2;
				
				minutes.setVisibility(View.VISIBLE);
				longClickIsActivated = false;
				handler.removeCallbacks(minute_clignoter);
				
				if (cligno_jour_lettres%2 == 0){
					joursLettres.setVisibility(View.INVISIBLE);
				}
				else{
					joursLettres.setVisibility(View.VISIBLE);
				}
				
				if (BIsClicked == true){
					
					intJour_nom = (intJour_nom + 1)%8;
					handler.post(attribuer_jour);
					
					BIsClicked = false;
				}
				
				handler.postDelayed(nom_jour_clignoter, 400);
				cligno_jour_lettres++;
			}
		};
		
		jour_clignoter = new Runnable(){
			@Override
			public void run(){
				date = 3;
				
				joursLettres.setVisibility(View.VISIBLE);
				handler.removeCallbacks(nom_jour_clignoter);
				
				if (cligno_jours%2 == 0){
					jours.setVisibility(View.INVISIBLE);
				}
				else{
					jours.setVisibility(View.VISIBLE);
				}
				
				if (BIsClicked == true){
					intJour_nb = Integer.parseInt(jours.getText().toString());
					jours.setText(String.valueOf((intJour_nb+1)%32));
					
					BIsClicked = false;
				}
				
				handler.postDelayed(jour_clignoter, 400);
				cligno_jours++;
			}
		};
		
		mois_clignoter = new Runnable(){
			@Override
			public void run(){
				date = 4;
				
				jours.setVisibility(View.VISIBLE);
				handler.removeCallbacks(jour_clignoter);
				
				if (cligno_mois%2 == 0){
					mois.setVisibility(View.INVISIBLE);
				}
				else{
					mois.setVisibility(View.VISIBLE);
				}
				
				if (BIsClicked == true){
					intMois = Integer.parseInt(mois.getText().toString());
					mois.setText(String.valueOf((intMois+1)%13));
					
					BIsClicked = false;
				}
				
				handler.postDelayed(mois_clignoter, 400);
				cligno_mois++;
			}
		};
		
		batterie_clignoter = new Runnable(){
			@Override
			public void run(){
				if (compteur_batterie%2 == 0){
					batterieNiveau1.setVisibility(View.INVISIBLE);
				}else{
					batterieNiveau1.setVisibility(View.VISIBLE);
				}
				
				handler.postDelayed(batterie_clignoter, 400);
				compteur_batterie++;
			}
		};
		
		boutonA.setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						switch (date){
						
						
						case 5:
							switch (nb_clic_boutonA){
							
								case 0:
									ecran1.setVisibility(View.GONE);
									ecran2.setVisibility(View.VISIBLE);
									ecran3.setVisibility(View.GONE);
									ecran4.setVisibility(View.GONE);
									ecran5.setVisibility(View.GONE);
									
									nb_clic_boutonA++;
									break;
									
								case 1:
									ecran1.setVisibility(View.GONE);
									ecran2.setVisibility(View.GONE);
									ecran3.setVisibility(View.VISIBLE);
									ecran4.setVisibility(View.GONE);
									ecran5.setVisibility(View.GONE);
									nb_clic_boutonA++;
									break;
									
								case 2:
									ecran1.setVisibility(View.GONE);
									ecran2.setVisibility(View.GONE);
									ecran3.setVisibility(View.GONE);
									ecran4.setVisibility(View.VISIBLE);
									ecran5.setVisibility(View.GONE);
									nb_clic_boutonA++;
									break;
									
								case 3:
									ecran1.setVisibility(View.GONE);
									ecran2.setVisibility(View.GONE);
									ecran3.setVisibility(View.GONE);
									ecran4.setVisibility(View.GONE);
									ecran5.setVisibility(View.VISIBLE);
									nb_clic_boutonA++;
									break;
									
								case 4:
									ecran1.setVisibility(View.VISIBLE);
									ecran2.setVisibility(View.GONE);
									ecran3.setVisibility(View.GONE);
									ecran4.setVisibility(View.GONE);
									ecran5.setVisibility(View.GONE);
									nb_clic_boutonA = 0; //Retour au début 
									break;
									
								default:
									break;
							}
							break;
						
						case 0 :
							handler.post(minute_clignoter);
							break;
						
						case 1:
							handler.post(nom_jour_clignoter);
							break;
						
						case 2:
							handler.post(jour_clignoter);
							break;	
							
						case 3:
							handler.post(mois_clignoter);
							break;
							
						case 4:
							handler.post(heure_clignoter);
							break;
							
						default:
							break;
					}
				}
			}
		);
		
		boutonA.setOnLongClickListener(
				new View.OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						
						if (nb_appels_bouton_A%2 == 0){
							longClickIsActivated = true;
							handler.post(heure_clignoter);
							
						}
						else{
							heures.setVisibility(View.VISIBLE);
							minutes.setVisibility(View.VISIBLE);
							joursLettres.setVisibility(View.VISIBLE);
							jours.setVisibility(View.VISIBLE);
							mois.setVisibility(View.VISIBLE);
							longClickIsActivated = false;
							handler.removeCallbacks(heure_clignoter);
							handler.removeCallbacks(minute_clignoter);
							handler.removeCallbacks(nom_jour_clignoter);
							handler.removeCallbacks(jour_clignoter);
							handler.removeCallbacks(mois_clignoter);
							date = 5;
						}
						
						nb_appels_bouton_A++;
						return true;
					}
				}			
		);
		
		boutonB.setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						BIsClicked = true;
					}
				}
		);
		
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
			
			case R.id.itemPointMort:
				if(nb_clic_point_mort%2 == 0){
					pointMort.setVisibility(View.VISIBLE);
					vitesses.setVisibility(View.INVISIBLE);
					//il n'y a plus de mouvement du véhicule
					rlAiguille.setRotation(45);
					rlAiguilleTours.setRotation(24);
				}
				else{
					pointMort.setVisibility(View.INVISIBLE);
				}
				nb_clic_point_mort++;
				return true;
				
			case R.id.itemVitesses:
				//pointMort.getVisibility()==4 -> point mort invisible
				//pointMort.getVisibility()==0 -> point mort visible
				if(pointMort.getVisibility()==4){
					rlAiguille.setPivotX((rlAiguille.getWidth())/2);
					rlAiguille.setPivotY((rlAiguille.getHeight())/2);
					
					
					fltVitesse = Float.valueOf(etVitesse.getText().toString());
					angleVitesse = 45 + 135*fltVitesse/90;
							
					rlAiguille.setRotation(angleVitesse);
					
					rlAiguilleTours.setPivotX((rlAiguilleTours.getWidth())/2);
					rlAiguilleTours.setPivotY((rlAiguilleTours.getHeight())/2);
					
					// fltTours a calculer en fonction de numbVitBoite et de fltVitesse
					/* si vitesse augmente, vitesse moteur egalement
					 * mais si le numbVitBoite augmente, cela diminue la vitesse moteur
					 * après dans les détails besoin des GET pour le poids de la moto, 
					 * le diamètre des roues, le rayon etc...
					 */
					fltTours = fltVitesse;
					angleTours = 24 + 89 * fltTours / 3;
					
					rlAiguilleTours.setRotation(angleTours);
				}
				else{
					Toast.makeText(getApplicationContext(),
                            "Noubliez pas le frein à main avant de démarrer !", Toast.LENGTH_LONG).show();
				}
				return true;
				
			case R.id.itemVitesseBoite:
				//si le nombre rentré est entre 1 et 6 inclus c'est bon
				// sinon on ne fait rien et on met un toast d'erreur
				// dans le cas où c'est bon -> 
				int numbVitBoite = Integer.parseInt(etBoiteVit.getText().toString());
				String numbVitB = etBoiteVit.getText().toString();
				if((numbVitBoite>=0) && (numbVitBoite<=6)){
		           if(numbVitBoite==0){
		        	   vitesses.setVisibility(View.INVISIBLE);
		        	   pointMort.setVisibility(View.VISIBLE);
		           }
		           else{
					   vitesses.setText(numbVitB);
			           vitesses.setVisibility(View.VISIBLE);
			           pointMort.setVisibility(View.INVISIBLE);
		           }
				}
				else{
					vitesses.setVisibility(View.INVISIBLE);
					Toast.makeText(getApplicationContext(),
                            "Valeur de boîte de vitesse impossible", Toast.LENGTH_LONG).show();
					pointMort.setVisibility(View.VISIBLE);
				}
				return true;
				
			case R.id.itemBatteryLevel:
				
				niveau_batterie = Integer.parseInt(etVitesse.getText().toString());
				String niveau_bat = etVitesse.getText().toString() + "%";
				niveauBatterie.setText(niveau_bat);
				
				if (niveau_batterie == 0){
					batterieNiveau1.setVisibility(View.INVISIBLE);
					batterieNiveau2.setVisibility(View.INVISIBLE);
					batterieNiveau3.setVisibility(View.INVISIBLE);
					batterieNiveau4.setVisibility(View.INVISIBLE);
				}
				else if (niveau_batterie < 5){
					batterieNiveau1.setVisibility(View.VISIBLE);
					batterieNiveau2.setVisibility(View.INVISIBLE);
					batterieNiveau3.setVisibility(View.INVISIBLE);
					batterieNiveau4.setVisibility(View.INVISIBLE);
					
					//handler.removeCallbacks(batterie_clignoter);
					//TODO Faire clignoter le layout
				}
				else if (niveau_batterie < 25){
					batterieNiveau1.setVisibility(View.VISIBLE);
					batterieNiveau2.setVisibility(View.INVISIBLE);
					batterieNiveau3.setVisibility(View.INVISIBLE);
					batterieNiveau4.setVisibility(View.INVISIBLE);
				}
				else if (niveau_batterie < 50){
					batterieNiveau1.setVisibility(View.VISIBLE);
					batterieNiveau2.setVisibility(View.VISIBLE);
					batterieNiveau3.setVisibility(View.INVISIBLE);
					batterieNiveau4.setVisibility(View.INVISIBLE);
				}
				else if (niveau_batterie < 75){
					batterieNiveau1.setVisibility(View.VISIBLE);
					batterieNiveau2.setVisibility(View.VISIBLE);
					batterieNiveau3.setVisibility(View.VISIBLE);
					batterieNiveau4.setVisibility(View.INVISIBLE);
				}
				else if (niveau_batterie < 101){
					batterieNiveau1.setVisibility(View.VISIBLE);
					batterieNiveau2.setVisibility(View.VISIBLE);
					batterieNiveau3.setVisibility(View.VISIBLE);
					batterieNiveau4.setVisibility(View.VISIBLE);
				}
				else{
					niveauBatterie.setText("?");
				}
				return true;
				
				
			case R.id.itemNiveauEssence:
				niveau_essence = Integer.parseInt(etVitesse.getText().toString());
				
				if (niveau_essence == 0){
					essenceNiveau1.setVisibility(View.INVISIBLE);
					essenceNiveau2.setVisibility(View.INVISIBLE);
					essenceNiveau3.setVisibility(View.INVISIBLE);
					essenceNiveau4.setVisibility(View.INVISIBLE);
					essenceNiveau5.setVisibility(View.INVISIBLE);
					essenceNiveau6.setVisibility(View.INVISIBLE);
				}
				else if (niveau_essence < 16){
					essenceNiveau1.setVisibility(View.VISIBLE);
					essenceNiveau2.setVisibility(View.INVISIBLE);
					essenceNiveau3.setVisibility(View.INVISIBLE);
					essenceNiveau4.setVisibility(View.INVISIBLE);
					essenceNiveau5.setVisibility(View.INVISIBLE);
					essenceNiveau6.setVisibility(View.INVISIBLE);
				}
				else if (niveau_essence < 33){
					essenceNiveau1.setVisibility(View.VISIBLE);
					essenceNiveau2.setVisibility(View.VISIBLE);
					essenceNiveau3.setVisibility(View.INVISIBLE);
					essenceNiveau4.setVisibility(View.INVISIBLE);
					essenceNiveau5.setVisibility(View.INVISIBLE);
					essenceNiveau6.setVisibility(View.INVISIBLE);
				}
				else if (niveau_essence < 50){
					essenceNiveau1.setVisibility(View.VISIBLE);
					essenceNiveau2.setVisibility(View.VISIBLE);
					essenceNiveau3.setVisibility(View.VISIBLE);
					essenceNiveau4.setVisibility(View.INVISIBLE);
					essenceNiveau5.setVisibility(View.INVISIBLE);
					essenceNiveau6.setVisibility(View.INVISIBLE);
				}
				else if (niveau_essence < 67){
					essenceNiveau1.setVisibility(View.VISIBLE);
					essenceNiveau2.setVisibility(View.VISIBLE);
					essenceNiveau3.setVisibility(View.VISIBLE);
					essenceNiveau4.setVisibility(View.VISIBLE);
					essenceNiveau5.setVisibility(View.INVISIBLE);
					essenceNiveau6.setVisibility(View.INVISIBLE);
				}
				else if (niveau_essence < 75){
					essenceNiveau1.setVisibility(View.VISIBLE);
					essenceNiveau2.setVisibility(View.VISIBLE);
					essenceNiveau3.setVisibility(View.VISIBLE);
					essenceNiveau4.setVisibility(View.VISIBLE);
					essenceNiveau5.setVisibility(View.VISIBLE);
					essenceNiveau6.setVisibility(View.INVISIBLE);
				}
				else if (niveau_essence < 101){
					essenceNiveau1.setVisibility(View.VISIBLE);
					essenceNiveau2.setVisibility(View.VISIBLE);
					essenceNiveau3.setVisibility(View.VISIBLE);
					essenceNiveau4.setVisibility(View.VISIBLE);
					essenceNiveau5.setVisibility(View.VISIBLE);
					essenceNiveau6.setVisibility(View.VISIBLE);
				}
				else{
					
				}
				
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
					feuxCroisement.setBackgroundResource(R.color.green);
				}
				else{
					feuxRoute.setBackgroundResource(R.color.bck_color);
					feuxCroisement.setBackgroundResource(R.color.bck_color);
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
				
			case R.id.itemOilLevel:
				if (nb_clic_essence%2 == 0){
					essence1.setBackgroundResource(R.color.orange);
					essence2.setBackgroundResource(R.color.orange);
					essence3.setBackgroundResource(R.color.orange);
					essence4.setBackgroundResource(R.color.orange);
					essence5.setBackgroundResource(R.color.orange);
				}
				else{
					essence1.setBackgroundResource(R.color.bck_color);
					essence2.setBackgroundResource(R.color.bck_color);
					essence3.setBackgroundResource(R.color.bck_color);
					essence4.setBackgroundResource(R.color.bck_color);
					essence5.setBackgroundResource(R.color.bck_color);
				}
				nb_clic_essence++;
				return true;
		}
		
		return false;
	}
}
