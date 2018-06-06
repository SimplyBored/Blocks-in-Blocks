import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class FinalField extends GameDriver {
	boolean lPressed, jPressed, iPressed, uPressed; //blue movement
	boolean dPressed, aPressed, wPressed, qPressed; //red movement
	boolean kPressed, sPressed, oPressed, ePressed; //power moves
	boolean pPressed;
	boolean paused;
	boolean nPressed, mPressed; //shortcut
	boolean spacePressed, enterPressed;
	boolean doneFading;
	boolean ins = false;
	boolean read = false;
	boolean play = false;
	boolean title = true;
	boolean done;
	boolean redWin, blueWin;
	//boolean rIncentive = true, bIncentive = true;
	boolean rIncentive, bIncentive;
	boolean lastScene;
	boolean enableCS = false;
	boolean musicBV, musicRV, musicOrigin = true, ECS, news, tutorial, game, block, Setup;
	int keep = -1;
	int pO = 20; //prevent overlap
	int scoreLimit = 15;
	boolean tutorialDone = false;
	boolean cutSceneDone, startEnding, finaleFade;
	boolean switched = false, finalRedDead, finalBlueDead;
	boolean grenadeBoom, bulbBreak, cartoonJump, teleport, bullet, laserSound,pin;
	int tutorialTimer = 0; 
	int tT = 0; //talkTime
	int s = 0; //seconds
	
	boolean endGame;
	int sEG = 0;//seconds endGame timer
	int fEG = 0;//frame endGame timer
	
	int sF = 0;//seconds finale timer
	int fF = 0;//frame finale timer
	Color whiteFade = new Color(255, 255, 255, 0);
	
	
	//boolean secondary, dead, jump;
	boolean redDead, blueDead;
	boolean laserOnField, portalOnField;
	Rectangle bg = new Rectangle(0,0,1300, 1000);
	Rectangle fade = new Rectangle(0,0,1300, 1000);
	Rectangle checkBox = new Rectangle(10, 500, 20, 20);
	Color pauseTrans = new Color(0, 0, 0,170);
	Rectangle pauseScreen = new Rectangle(0, 0, 1300, 1000);
	SmartParticle blueCSBlood = new SmartParticle(780, 550, 55, 2, Color.RED);
	Hero redHero = new Hero(910,500, 20,20, Color.RED);
	Hero blueHero = new Hero(290,500, 20,20, Color.BLUE);
	Hero doctor = new Hero(1300, 550, 20, 20, Color.YELLOW);
	Hero son = new Hero(1300,550, 20, 20, Color.BLUE);
	Color redsGrenade = Color.PINK;
	Color bluesGrenade = new Color(9, 255, 38);
	SmartParticle redHold = new SmartParticle(950, 20, 200, 10, Color.WHITE);
	SmartParticle blueHold = new SmartParticle(50, 20, 200, 10, Color.WHITE);
	SmartParticle redGHold = new SmartParticle(950, 32, 200, 5, Color.WHITE);
	SmartParticle blueGHold = new SmartParticle(50, 32, 200, 5, Color.WHITE);
	SmartParticle redPowerReady = new SmartParticle(950, 20, 10, 10, Color.RED);
	SmartParticle redGReady = new SmartParticle(950, 32, 10, 5, redsGrenade);
	SmartParticle bluePowerReady = new SmartParticle(50, 20, 10, 10, Color.BLUE);
	SmartParticle blueGReady = new SmartParticle(50, 32, 10, 5, bluesGrenade);
	//Path redPath = new Path(redHero);
	ArrayList <SmartBullet> redBullets;
	ArrayList<SmartBullet> blueBullets;
	ArrayList<SmartParticle> additions;
	ArrayList<SmartBullet> grenade;
	ArrayList <Effects> towerBoom;
	//Color backDrop = new Color(55, 45, 50);
	Color backDrop = new Color(0, 0, 30);
	Color fadeColor = new Color(0, 0, 0, 0);
	SoundDriver soundEffects;
	SoundDriver music;
	int redScore = 0;
	int blueScore = 0;
	int animex, animey;
	Font chatFont = new Font("Verdana", Font.PLAIN, 20);
	
	Color titleC = new Color(255, 211, 193);
	
	Arena fightGround;
	int redBulletCnt = 20; //stops bullet spam
	int blueBulletCnt = 20;
	int envLaserCnt = 1; //for enviornment counter
	int envPortalCnt = 0;
	int portalRegulator = 100;
	int laserRegulator = 400;//for laser spawn time
	int laserFrequency = 1000; //increases laser spawn as match progresses
	//Basic Movement test blocks

	
	
	Color c1 = new Color(102,51,0);
	Effects bomb = new Effects(); //for heros
	Effects grenadeExplosion = new Effects(); //for effective particle damage
	Effects teadrs = new Effects();
	Laser laser = new Laser(fightGround);
	Portals portal = new Portals();
	//Effects platformExplosion = new Effects();

	public FinalField(){
		
		String[] soundArray = new String[8];
		soundArray[0] = "bulbBreak.wav";
		soundArray[1] = "flashbang.wav";
		soundArray[2] = "jump.wav";
		soundArray[3] = "teleport.wav";
		soundArray[4] = "bullet.wav";
		soundArray[5] = "laser.wav";
		soundArray[6] = "pin.wav";
		soundArray[7] = "block.wav";
		soundEffects = new SoundDriver(soundArray);
		String[] bMusic = new String[8];
		bMusic[0] = "Chosen.wav";
		bMusic[1] = "Setup.wav";
		bMusic[2] = "bWin.wav";
		bMusic[3] = "rWin.wav";
		bMusic[4] = "ECS.wav";
		bMusic[5] = "teleport.wav";
		bMusic[6] = "Tutorial.wav";
		bMusic[7] = "game.wav";
		music = new SoundDriver(bMusic);
		//redPath = new Path(redHero);
		grenade = new ArrayList<SmartBullet>();
		additions = new ArrayList<SmartParticle>();
		redBullets = new ArrayList<SmartBullet>();
		blueBullets = new ArrayList<SmartBullet>();
		towerBoom = new ArrayList<Effects>();
		fightGround = new Arena(false);
	}
	
	
	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		
		music.setVolume(-10f);
		
		if(bulbBreak){soundEffects.playCon(0); bulbBreak = false;}
		if(grenadeBoom){soundEffects.playCon(1); grenadeBoom = false;}
		if(cartoonJump){soundEffects.playCon(2); cartoonJump = false;}
		if(teleport){soundEffects.playCon(3); teleport = false;}
		if(bullet){soundEffects.playCon(4); bullet = false;}
		if(laserSound){soundEffects.playCon(5); laserSound = false;}
		if(pin){soundEffects.playCon(6); pin = false;}
		if(block){soundEffects.playCon(7); block = false;}
		if(musicOrigin){music.setVolume(0);music.loop(0); musicOrigin = false;}
		if(Setup){music.loop(1); Setup=false;}
		if(musicBV){music.setVolume(1);music.play(2); musicBV = false;}
		if(musicRV){music.play(3); musicRV = false;}
		if(ECS){music.play(4); ECS = false;}
		if(news){music.play(5); news = false;}
		if(tutorial){music.loop(6); tutorial = false;}
		//if(game){music.loop(7); game = false;}
		 
		
		
		
		
		
		
		if(title){
			if(musicOrigin){music.setVolume(0);music.loop(1); musicOrigin = false;}
			Talking bT = new Talking(Color.CYAN, chatFont, blueHero, win); //blue talk 
			Talking rT = new Talking(Color.PINK, chatFont, redHero, win); //red talk
			win.setColor(backDrop);//creates background
			win.fill(bg);
			
			preliminaryFunctions(win);
			ArrayList<SmartParticle> keep = fightGround.getArena();
			KIA();
			
			win.setColor(Color.WHITE);
			win.setFont(new Font("Forte", Font.BOLD, 50));
			win.drawString("*Place Original Game Title Here*", 200, 200);
			win.setColor(Color.CYAN);
			win.setFont(new Font("Forte", Font.PLAIN, 40));
			win.drawString("*Some cliche catchphrase*", 400, 250);
			win.setFont(new Font("Forte", Font.PLAIN, 20));
			win.drawString("Team 511: Jake Hines", 515, 290);
			win.setFont(new Font("Haettenschweiler", Font.PLAIN, 30));
			win.setColor(Color.GREEN);
			win.drawString("[p]", 150, 400);
			win.drawString("How to Play", 100, 440);
			
			if(read){
				win.setColor(Color.YELLOW);
				win.drawString("[s]", 1040, 400);
				win.drawString("Start", 1025, 440);
			}else{
				win.setColor(Color.YELLOW);
				win.drawString("Watch Tutorial First", 900,400);//difference of 65
				win.drawString("Start", 965, 440);
			}
			win.setColor(Color.MAGENTA);
			win.drawString("[spacebar]", 560, 400);
			win.drawString("Tutorial", 568,440);
			//read = true;
			
			if(spacePressed && pO > 19){
				ins = true;
				play = false;
				title = false;
				fightGround = new Arena(false);
				endGame = false;
				tutorial = true;
				music.stop(0);
			}
			if(sPressed && pO > 19 && read){
				fightGround = new Arena(true);
				ins = false;
				play = true;
				title = false;
				endGame = false;
				game = true;
				music.stop(0);
			}
			if(enterPressed && pO >19){
				pO = 0;
				enableCS = !enableCS;
			}
			if(!enableCS){
				win.setColor(Color.RED);
				win.drawString("[Enter]", 573, 510);
				win.drawString("UNNECESSARY DRAMA DISABLED", 470, 540);
			}else{
				win.setColor(Color.GREEN);
				win.drawString("[Enter]", 573, 510);
				win.drawString("UNNECESSARY DRAMA ENABLED", 473, 540);
			}
		}
		if(ins){
			//read = true;
			Talking bT = new Talking(Color.CYAN, chatFont, blueHero, win); //blue talk 
			Talking rT = new Talking(Color.PINK, chatFont, redHero, win); //red talk
			win.setColor(backDrop);//creates background
			win.fill(bg);
			
			preliminaryFunctions(win);
			drawExtra(win);
			
			ArrayList<SmartParticle> keep = fightGround.getArena();
			
			KIA();
			if(tT == 0){tutorial = true;}
			if(s > 1){rT.talk("Hello! I'm Red!");}
			if(s > 3){rT.mute();}
			if(tT == 5*25){music.stop(6);}
			if(s > 4){bT.talk("And I'm adopted");}
			if(tT == 155){redHero.setDy(-10); cartoonJump = true;}
			if(s > 6){bT.mute();} //6 sec
			if(s > 8){rT.talk("OH mY GAhD YoU kNEw ?!/!/!?");}
			if(s > 10){rT.mute();}
			if(s > 11){bT.talk("WE'RE TWO DIFFERENT COLORS");}
			if(s > 13){bT.mute();}
			if(tT > 350 && tT <525){bomb.genTears(redHero, Color.BLUE);} //13
			if( s > 15){bT.talk("Are you crying?");}
			if(s > 17){bT.mute(); rT.talk("*sniffle*");}
			if(s > 18){rT.talk("no..");}
			if(s > 19){rT.mute();}
			if(s > 21){rT.talk("Well, anyway..."); }
			if(tT == 21*25){tutorial = true;}
			if(s > 22){rT.talk("We are going to teach you how to play *insert name of game here*");}
			if(s > 26){bT.talk("\"a\" moves me left, \"d\" moves me right, and \"w\" makes me jump"); rT.mute();}
			if(s > 32){bT.mute();}
			if(s > 32){rT.talk("\"j\" moves me left, \"l\" moves me right, and \"i\" makes me jump");}
			if(s > 38){bT.talk("No one cares, Red");}
			if(s > 39){bT.mute(); rT.mute();}
			if(s > 40){rT.talk("rude.....");}
			if(s > 41){rT.mute();}
			if(s > 42){bT.talk("I can shoot bullets by pressing \"q\"");}
			if(s > 46){bT.mute(); rT.talk("And I shoot bullets by pressing \"u\"");}
			if(s > 50){rT.mute();}
			if(s > 50){rT.talk("Bullets shoot in the direction of your motion");}
			if(s > 54){bT.talk("And can deflect off walls"); rT.mute();}
			if(s > 56){bT.talk("Like this"); rT.mute();}
			if(s > 58){bT.mute();}
			if(tT > 58*25 && tT <59*25){blueHero.setDx(-10);}
			if(tT == 59*25){blueHero.setDy(-10); genBullet(blueHero, blueBullets, 21); blueHero.setDx(0); cartoonJump = true;}
			if(s > 61 && !redDead && s < 63){rT.talk("wait....");}
			if(s > 64 && s < 68){if(!redDead)rT.talk("OWWWWW"); blueHero.setDx(3);}
			if(s > 66 && !redDead){rT.talk("THAT HURT");}
			if(s > 67 && !redDead){rT.talk("Do not do that agai-");}
			if(s > 69){rT.mute();}
			if( s == 68){blueHero.setDx(0);}
			if(s == 70){bye();}
			if(s > 72 && !redDead){rT.talk("Hey! What did I just say!?!?!");}
			if(s > 73){bT.talk("You can also create platforms when the big bar above is full");}
			if(s > 74){rT.talk("You can't just-");}
			if(s > 77){rT.talk("ARE YOU LISTENING"); bT.talk("These platforms are generated in four directions and OPPOSITE your motion");}
			if(s > 79){rT.talk("You know what...");}
			if(s > 81){rT.mute();}
			if(tT == 80*26){redHero.setDx(-10);genBullet(redHero, redBullets, 21); redHero.setDx(0);}
			if(tT == 81*25){blueHero.setDy(-10); cartoonJump = true;}
			if(tT == 81*25 + 10){genBlock(blueHero);}
			if(s > 83){bT.talk("Enemy bullets become yours when contacting your platforms");}
			if(tT == 85*25){blueBullets.remove(blueBullets.size()-1);}
			if(s > 87){bT.mute();}
			if(tT == 88*25 + 12){blueHero.setDx(-5); genBlock(blueHero);}
			if(tT == 88*25 + 12 + 25){blueHero.setDx(0);} 
			if(s > 89){bT.talk("And can kill while in transit");}
			if(s > 91){bT.mute();}
			if(s > 93 && !redDead){rT.talk("I already hate this game");}
			if(tT == 93*25){ bomb.genExplosion(keep.remove(keep.size()-2));}
			if(s > 95){rT.mute();}
			if(s > 95){bT.talk("But this game has so many cool features!");}
			if(s > 98){bT.talk("Like phasing through walls!");}
			if(s > 100){bT.mute(); rT.talk("That's only because the programmer doesn't know how to fix it");}
			if(s > 102){rT.mute();}
			if(tT == 103*25){ laser.genLaserPoint(fightGround, redHero); laserSound = true;}
			if(s > 103){bT.talk("And Lasers!");}
			if(s > 106){bT.mute();}
			if(s > 107){rT.talk("IS THERE ANYTHING GOOD ABOUT THIS GAME!");}
			if(s > 109){bT.talk("Actually, yes"); rT.mute();}
			if(s > 110){bT.talk("If the small bar above is full, you can activate a special block");}
			if(s > 114){bT.talk("For me I press \"e\", and for you it's \"o\"");}
			if(s > 116){bT.talk("Red, jump for me and press \"o\"");}
			if(s > 118){bT.mute(); rT.talk("Ok");}
			if(s > 119){rT.mute();}
			if(tT == 120*25){redHero.setDy(-10); genGrenade(redHero); cartoonJump = true;}
			if(s > 121){rT.talk("What is it?");}
			if(s > 122){rT.talk("A ball?");}
			if(s > 123){bT.talk("Nope"); rT.mute();}
			if(s >  124){bT.mute();}
			if(s > 125){bT.talk("A grenade");}
			if(s > 129 && !redDead){rT.talk("$#&*^$#*!"); bT.mute();}
			if(s > 130){rT.talk("WHY");}
			if(s > 131){bT.talk("Grenades also travel in the direction of your motion"); rT.mute();}
			if(s  > 133){bT.mute();}
			if(tT == 134*25){portal.genPortalsForced(fightGround);}
			if(tT == 135*25 +10){redHero.setDy(-10); cartoonJump = true;}
			if(s > 136){rT.talk("What are THOSE?");}
			if(s > 138){rT.mute(); bT.talk("Those are portals");}
			if(s > 140){bT.talk("They can teleport projectiles or people");}
			if(tT == 142*25){blueHero.setDy(-10);genBullet(blueHero, blueBullets, 21);}
			if(s > 146 && !redDead){rT.talk("HA! You Missed!");} 
			if(s > 146){bT.mute();}
			if(tT == 149*25){laser.genLaserPoint(fightGround, redHero); laserSound = true;}
			if(s > 151){rT.mute(); bT.mute();}
			if(s > 152){bT.talk("Alright!");}
			if(s > 154){bT.talk("First one to 15 Wins!");}
			if(s > 156){bT.talk("Press the spacebar to begin!"); tutorialDone = true; rT.talk("You can pause during the game by pressing [p]");}
			if(s > 170){rT.talk("Why are you still here?");}
			if(s > 174){rT.talk("LEAVE");}
			
		
			//if(s == 1){s= 153; tT = s*25;}
			
			particleRemove();
			//regualtes tutorial
			
			if(read && s>2){
				win.setFont(new Font("Informal Roman", Font.BOLD, 35));
				win.setColor(Color.GREEN);
				win.drawString("Skip[s]", 1050, 530);
				if(sPressed){
					play = true;
					ins = false;
					fightGround = new Arena(true);
					redScore = 0;
					blueScore = 0;
					read = true;
					//musicOrigin = true;
					music.stop(6);
					game = true;
				}
			}
	
			if(spacePressed && tutorialDone){
				play = true;
				ins = false;
				fightGround = new Arena(true);
				redScore = 0;
				blueScore = 0;
				read = true;
				//musicOrigin = true;
				music.stop(6);
				game = true;
			}
			
			if(sPressed && iPressed){
				play = true;
				ins = false;
				fightGround = new Arena(true);
				redScore = 0;
				blueScore = 0;
				read = true;
				//musicOrigin = true;
				music.stop(6);
				game = true;
				if(music.isPlaying(5)){
					music.stop(5);
				}
			}
			/*
			if(tT > 10){
				tutorialDone = true;
			}
			*/
			tT ++;
			if(tT%25 == 0 && tT !=0){
				s++;
			}
			rT.getConvo();
			bT.getConvo();
		}
		if(play){
			if(nPressed && mPressed)
			{
				redScore = 14;
				blueScore = 14;
			}
			win.setColor(backDrop);
			win.fill(bg);
			win.setColor(Color.RED);
			
			if(envLaserCnt%laserRegulator == 0){
				laserOnField = true;
				if(laserFrequency > 300){
					laserFrequency -= 25;
				}
				laserRegulator = (int)(Math.random()*laserFrequency + 100);//randomizes laser
			}
			
			if(envPortalCnt == 100){ //refer to bye method for restart 
				portalOnField = true; //gives go ahead for portal spawn
			}
			
			if(laserOnField){
				laser.genLaser(fightGround);
				laserOnField = false;
				laserSound = true;
			}
			
			if(portalOnField){//spawns portal
				portal.genPortals(fightGround);
				portalOnField = false;//but only 1 set
			}
			
			if(portal.teleport(blueHero)){teleport = true;}
			if(portal.teleport(redHero)){teleport = true;}
			
			MAD(win);//draws bullet
			for(int i = 0; i < additions.size(); i++){
				if(fightGround.addPlat(additions.get(i))){
					additions.remove(i);
					i--;
				}
			}//when colliding with arena the additions are deleted
			
			ArrayList<SmartParticle> keep = fightGround.getArena();//builds arena
			
			
			
			
			controls();
			
			
			for(int i = 0; i < keep.size(); i++){
				if(!redHero.intersects(keep.get(3))){
					redHero.jump(iPressed, keep.get(i)); //hero can jump with arena while not in contact with top
				}else if(redHero.intersects(keep.get(3))){
					redHero.move(redHero.x, keep.get(3).y + keep.get(3).height);
				}//if intersects top then moved to bottom
				if(!blueHero.intersects(keep.get(3))){
					blueHero.jump(wPressed, keep.get(i)); //hero can jump with arena while not in contact with top
				}else if(blueHero.intersects(keep.get(3))){
					blueHero.move(blueHero.x, keep.get(3).y + keep.get(3).height);
				}//if intersects top then moved to bottom
				redHero.heroCollision(keep.get(i));//hero is limited
				blueHero.heroCollision(keep.get(i));
				for(int j = 0; j < redBullets.size(); j++){
					SmartBullet projectile = redBullets.get(j);
					if(redBullets.get(j).bulletCollision(keep.get(i))){//bullets reflect off walls
						if(keep.get(i).getColor().equals(Color.CYAN)){
							projectile.setColor(Color.CYAN);
							blueBullets.add(projectile);
							redBullets.remove(j);
							j--;
						}
					}//
				}//bullets reflect off walls
				for(int j = 0; j < blueBullets.size(); j++){
					SmartBullet projectile = blueBullets.get(j);
					if(blueBullets.get(j).bulletCollision(keep.get(i))){
						if(keep.get(i).getColor().equals(Color.MAGENTA)){
							projectile.setColor(Color.MAGENTA);
							redBullets.add(projectile);
							blueBullets.remove(j);
							j--;
						}
					}
				}//bullets reflect off walls
				for(int k = 0; k < grenade.size(); k++){
					SmartBullet p = grenade.get(k);
					p.bulletCollision(keep.get(i));
					p.bulletCollision(blueHero);
					p.bulletCollision(redHero);
				}
				grenadeExplosion.explodeParticleReturn(keep.get(i));//return opposite velocity from effect class
				bomb.explodeCollision(keep.get(i));
				laser.explodeParticleReturn(keep.get(i));
			}//for everything interacting with the arena
			
			drawExtra(win);
			
			if(!redDead){
				 redHero.moveAndDrawHero(win, keep.get(2));//cause its a component of death
			}//extraneous
			if(!blueDead){
				 blueHero.moveAndDrawHero(win, keep.get(2));//cause its a component of death
			}//extraneous
			
			
			//redPath.genPath(redHero);
			//redPath.drawPath(win, redHero);
			//redPath.drawPath(win);
			
			bye(); //erases bullets
			//
			 
			
			//Death Explosion Effect
			KIA();
			
			particleRemove();
			/*
			for(int i = 0; i < keep.size(); i++){
				bomb.explodeCollision(keep.get(i));
			}
			*/
			//
			
			//Misc
			redBulletCnt ++;
			blueBulletCnt ++; //counter for bullet-spam stopper
			envLaserCnt++;
			envPortalCnt++;
			
			if(!done){
				if(redScore >=scoreLimit){
					redWin = true;
					done = true;
				}else if(blueScore >=scoreLimit){
					blueWin = true;
					done = true;
				}
			}
			
			if((redWin||blueWin)  && !endGame){fEG ++;}
			if(fEG < 52){
				fadeColor = new Color(0, 0, 0, 5*fEG);
			}
			win.setColor(fadeColor);
			win.fill(fade);
			if(fEG == 51){
				endGame = true;
				play = false;
				fEG = 125;
				for(int i = 0; i < soundEffects.length(); i++){
					if(soundEffects.isPlaying(i)){
						soundEffects.stop(i);
					}
				}
				for(int i = 0; i < music.length(); i++){
					if(music.isPlaying(i)){
						music.stop(i);
					}
				}
			}
			//win.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f));
		}//end playable game
		
		if(rIncentive && bIncentive && endGame){
			lastScene = true;
		}
		
		if(endGame && !enableCS){//for boring ending
			ArrayList<SmartParticle> keep = fightGround.getArena();
			win.setColor(backDrop);//creates background
			win.fill(bg);
			if(!doneFading){
				grenade = new ArrayList<SmartBullet>();
				additions = new ArrayList<SmartParticle>();
				redBullets = new ArrayList<SmartBullet>();
				blueBullets = new ArrayList<SmartBullet>();
				portal = new Portals();
				bomb = new Effects(); //for heros
				grenadeExplosion = new Effects(); //for effective particle damage
				fightGround = new Arena(tutorialDone);
				laser = new Laser(fightGround);
				play = false;
				fightGround = new Arena(false);
				fightGround.protect();
				blueHero.setDx(0); blueHero.setDy(0);
				redHero.setDx(0); redHero.setDy(0);
				if(blueWin){
					blueHero.move(keep.get(4).x + keep.get(4).width-blueHero.width + 7, keep.get(4).y - blueHero.height -2);
					redHero.move(keep.get(4).x + keep.get(4).width*3/2 + 40, keep.get(2).y - redHero.height -2);
				}else{
					redHero.move(keep.get(4).x + keep.get(4).width-blueHero.width + 7, keep.get(4).y - blueHero.height -2);
					blueHero.move(keep.get(4).x + keep.get(4).width*3/2 + 40, keep.get(2).y - redHero.height -2);
				}
				redDead = false;
				blueDead = false;
			}
			fightGround.moveAndDrawArena(win);
			if(fEG > 0 && !doneFading){
				fadeColor = new Color(0, 0, 0, fEG*2);
				fEG --;
			}
			if(fEG == 10){
				doneFading = true;
			}
			if(doneFading){
				win.setFont(new Font("Forte", Font.BOLD, 50));
				
				if(blueWin){
					win.setColor(Color.BLUE);
					win.drawString("Blue Wins!", 300, 150);
					blueScore =15;
				}else{
					redScore = 15;
					win.setColor(Color.RED);
					win.drawString("Red Wins!", 300, 150);
				}
				win.drawString("Press Space To Restart Game!", 300, 200);
				win.setColor(Color.BLUE);
				win.drawString("" + blueScore, 538, 395);
				win.setColor(Color.WHITE);
				win.drawString("-", 593, 395);
				win.setColor(Color.RED);
				win.drawString("" + redScore, 613, 395);
				if(spacePressed){
					pO = 0;
					reset();
				}
			}
			win.setColor(fadeColor);
			win.fill(fade);
		}
		
		if(endGame && blueWin && !lastScene && enableCS){//for blue win cutscene
			//blue ending
			Talking bT = new Talking(Color.CYAN, chatFont, blueHero, win); //blue talk 
			Talking rT = new Talking(Color.PINK, chatFont, redHero, win); //red talk
			
			ArrayList<SmartParticle> keep = fightGround.getArena();
			win.setColor(backDrop);//creates background
			win.fill(bg);
			
			fightGround.protect();
			fightGround.moveAndDrawArena(win);
			if(!doneFading){
				grenade = new ArrayList<SmartBullet>();
				additions = new ArrayList<SmartParticle>();
				redBullets = new ArrayList<SmartBullet>();
				blueBullets = new ArrayList<SmartBullet>();
				portal = new Portals();
				bomb = new Effects(); //for heros
				grenadeExplosion = new Effects(); //for effective particle damage
				fightGround = new Arena(tutorialDone);
				laser = new Laser(fightGround);
				play = false;
				fightGround = new Arena(false);
				blueHero.setDx(0); blueHero.setDy(0);
				redHero.setDx(0); redHero.setDy(0);
				blueHero.move(keep.get(4).x + keep.get(4).width-blueHero.width + 7, keep.get(4).y - blueHero.height -2);
				redHero.move(keep.get(4).x + keep.get(4).width*3/2 + 40, keep.get(2).y - redHero.height -2);
				redDead = false;
				blueDead = false;
			}
			
			
			
			
			
			if(fEG > 0 && !doneFading){
				fadeColor = new Color(0, 0, 0, fEG*2);
				fEG --;
			}
			if(fEG == 10 && !doneFading){
				doneFading = true;
				music.stop(1);
				musicBV = true;
				
			}
			
			KIA();
			preliminaryFunctions(win);
			
			if(doneFading){
				if(fEG == 0){musicBV = true;}
				if(sEG >= 0){rT.talk("Blue! "); }
				if(sEG >= 1){rT.talk("You don't have to do this!");}
				//if(fEG == 1*25 + 10){redHero.setDy(0);}
				if(sEG >= 3){rT.mute(); bT.talk("You lied to me! Who am I really?");}
				if(sEG >= 6){bT.talk("Who's my real family?!");}
				if(sEG >= 9){bT.mute(); rT.talk("We were going to tell you eventually! It's complicated!");}
				if(sEG >= 12){rT.mute(); bT.talk("Well now its too late");}
				if(sEG >= 15){bT.talk("Goodbye");}
				if(sEG >= 18){bT.mute(); rT.talk("BUT I AM YOUR BROTHER");}
				if(sEG >= 21){rT.mute(); bT.talk("*Foster");}
				if(fEG == 23*25){bT.mute(); killForced(blueHero, blueBullets, 12, 15); animex = redHero.x; animey = redHero.y;}
				if(sEG == 23 && redDead){redHero.setColorGone();redDead = true; blueBullets = new ArrayList<SmartBullet>(); finalRedDead = true; }
				if(fEG == 24 *25){music.pause(2);}
				if(sEG >= 23 && redDead){redHero.setColorGone(); redDead = true; }
				if(sEG >= 25){bT.talk("Now...");}
				if(sEG >=27){blueHero.setColor(Color.YELLOW);}
				if(sEG >= 27){bT.mute(); bT.setColor(Color.ORANGE);}
				if(sEG >= 28){bT.talk("Time to find the REAL Blue");}
				if(sEG >= 30){bT.mute();}
				if(sEG == 31){blueHero.setDx(5);}
				if(sEG == 32){blueHero.setDx(0);}
				if(sEG == 33){switched = true;}
				if(sEG >= 33){blueHero.setColor(Color.RED);}
				if(sEG >= 34){bT.talk("Thanks for the Color,");}
				if(fEG == 36*25){blueHero.setDx(5);}
				if(sEG >= 36){bT.mute();}
				if(fEG == 37*25){blueHero.setDx(0);}
				if(sEG >= 38){bT.setColor(Color.PINK);bT.talk("RED");}
				if(fEG == 38*25-6){music.resume(2);}
				if(fEG == 39*25){bomb.genExplosion(keep.remove(1)); blueHero.setDx(5);}
				if(sEG >= 39){bT.mute();}
				if(sEG <= 40  && finalRedDead && !switched){blueCSBlood.moveAndDraw(win);}
				
				
				
			//if(sEG == 0){sEG = 38; fEG = sEG*25;}
				
				if(fEG%25 == 0 && fEG != 0){sEG++;}
				fEG ++;
			}
			
			
			if(blueHero.x > 1250){
				win.setFont(new Font("Forte", Font.BOLD, 50));
				win.setColor(Color.BLUE);
				win.drawString("Blue Wins!", 300, 150);
				win.drawString("Press Space To Restart Game!", 300, 200);
				win.drawString("" + blueScore, 538, 395);
				win.setColor(Color.WHITE);
				win.drawString("-", 593, 395);
				win.setColor(Color.RED);
				win.drawString("" + redScore, 613, 395);
				blueScore = 15;
				if(spacePressed){
					pO = 0;
					reset();
					bIncentive = true;
					music.stop(2);
				}
			}
			
			
			particleRemove();
			

			
			
			rT.getConvo();
			bT.getConvo();
			
			win.setColor(fadeColor);
			win.fill(fade);
			
		}
		if(endGame && redWin &&!lastScene && enableCS){//for red win cutscene
			//red ending
			Talking bT = new Talking(Color.CYAN, chatFont, blueHero, win); //blue talk 
			Talking rT = new Talking(Color.PINK, chatFont, redHero, win); //red talk
			
			ArrayList<SmartParticle> keep = fightGround.getArena();
			win.setColor(backDrop);//creates background
			win.fill(bg);
			
			fightGround.protect();
			fightGround.moveAndDrawArena(win);
			if(!doneFading){
				grenade = new ArrayList<SmartBullet>();
				additions = new ArrayList<SmartParticle>();
				redBullets = new ArrayList<SmartBullet>();
				blueBullets = new ArrayList<SmartBullet>();
				portal = new Portals();
				bomb = new Effects(); //for heros
				grenadeExplosion = new Effects(); //for effective particle damage
				fightGround = new Arena(tutorialDone);
				laser = new Laser(fightGround);
				play = false;
				fightGround = new Arena(false);
				blueHero.setDx(0); blueHero.setDy(0);
				redHero.setDx(0); redHero.setDy(0);
				redHero.move(keep.get(4).x + keep.get(4).width-blueHero.width + 7, keep.get(4).y - blueHero.height -2);
				blueHero.move(keep.get(4).x + keep.get(4).width*3/2 + 40, keep.get(2).y - redHero.height -2);
				redDead = false;
				blueDead = false;
			}
			
			
			
			
			
			if(fEG > 0 && !doneFading){
				fadeColor = new Color(0, 0, 0, fEG*2);
				fEG --;
			}
			if(fEG == 10){
				doneFading = true;
				musicRV = true;
			}
			
			KIA();
			preliminaryFunctions(win);
			
			if(doneFading){
				if(sEG >= 1){rT.talk("It doesn't have to end like this!");}
				if(sEG >= 3){rT.talk("There's something I need to tell you!");}
				if(sEG >=5){rT.mute(); bT.talk("There's nothing you need to explain");}
				if(sEG >=7){bT.talk("I understand everything");}
				if(sEG >=9){bT.mute(); rT.talk("No you don't!");}
				if(sEG >=11){rT.talk("I can still save you if you just shut up and listen!");}
				if(sEG >=14){rT.mute(); bT.talk("Tell me who my real parents are!");}
				if(sEG >=16){bT.mute(); rT.talk("I-");}
				if(sEG >=18){bT.mute(); rT.talk("I don't know.....");}
				if(sEG >=20){rT.mute(); bT.talk("That's what I thought");}
				if(sEG >=21){bT.mute();}
				if(sEG >=22){bT.talk("GO TO HELL"); }
				if(sEG >= 24){rT.talk("BLUE");}
				if(sEG >= 25){bT.mute(); rT.talk("NO!");}
				if(fEG == 25*25 +10){killForced(blueHero, blueBullets, -10, -15);}
				if(fEG == 25*25 +20){killForced(blueHero, blueBullets, -2, -17.9);}
				if(fEG == 25*25 +30){killForced(blueHero, blueBullets, 7, -16.5);}
				//if(fEG == 25*25 +40){killForced(blueHero, blueBullets, 10, -15);}
				if(fEG == 25*25 + 17){redHero.setDy(-10); redHero.setDx(10);}
				if(sEG >= 26){rT.mute();}
				if(fEG == 26*25 + 15){killForced(redHero, redBullets, -7, 15);}
				if(fEG >= 27*25 && redHero.intersects(keep.get(2))){redHero.setDy(0);redHero.setDx(0);}
				if(fEG >=28*25 && blueDead){finalBlueDead = true;}
				if(fEG ==28*25 && finalBlueDead){blueBullets = new ArrayList<SmartBullet>();redBullets.remove(redBullets.size()-1); blueHero.setColorGone();}
				if(sEG >=31){rT.talk("GOD");redHero.setDx(-5);}
				if(sEG >=32){rT.mute();}
				if(sEG >=33){rT.talk("WE WERE MORE THAN BROTHERS");redHero.setDx(0);}
				if(sEG >=35){rT.mute(); redHero.setColor(Color.BLUE); rT.setColor(Color.CYAN);}
				if(sEG >=36){rT.talk("WE WERE CLONES, BLUE");}
				if(sEG>=38){rT.mute();}
				if(sEG >=39){rT.talk("we were clones");}
				if(sEG >=41){rT.mute();}
				if(sEG >=42){rT.talk("I'm going to find the doctor who did this,");}
				if(fEG == 43*25){bomb.genExplosion(keep.remove(1));}
				if(sEG >=44){rT.mute();}
				if(sEG >=45){rT.talk("And I'm going to kill him");}
				if(sEG >=47){rT.mute();redHero.setDx(5);}
					
			//stop clone outbreak from every happening		
					

				
				
				
				//if(sEG == 0){sEG = 45; fEG = sEG*25;}
				
				if(fEG%25 == 0 && fEG != 0){sEG++;}
				fEG ++;
			}
			
			
			if(redHero.x > 1250){
				win.setFont(new Font("Forte", Font.BOLD, 50));
				win.setColor(Color.RED);
				win.drawString("Red Wins!", 300, 150);
				win.drawString("Press Space To Restart Game!", 300, 200);
				win.setColor(Color.BLUE);
				win.drawString("" + blueScore, 538, 395);
				win.setColor(Color.WHITE);
				win.drawString("-", 593, 395);
				win.setColor(Color.RED);
				win.drawString("" + redScore, 613, 395);
				redScore = 15;
				if(spacePressed){
					pO = 0;
					reset();
					rIncentive = true;
				}
			}
			
			
			particleRemove();
			

			
			
			rT.getConvo();
			bT.getConvo();
			
			win.setColor(fadeColor);
			win.fill(fade);
		}
		if(lastScene && enableCS){//for finale cutscene
			//red ending
			Talking bT = new Talking(Color.CYAN, chatFont, blueHero, win); //blue talk 
			Talking rT = new Talking(Color.PINK, chatFont, redHero, win); //red talk
			Talking sT = new Talking(Color.CYAN, chatFont, son, win); //blue talk 
			Talking dT = new Talking(Color.ORANGE, chatFont, doctor, win);
			
				
			
			
			
			ArrayList<SmartParticle> keep = fightGround.getArena();
			if(!finaleFade){
				win.setColor(backDrop);//creates background
			}else{
				win.setColor(new Color(27, 34, 49));
			}
			win.fill(bg);
			
			
			if(!doneFading && !finaleFade){
				grenade = new ArrayList<SmartBullet>();
				additions = new ArrayList<SmartParticle>();
				redBullets = new ArrayList<SmartBullet>();
				blueBullets = new ArrayList<SmartBullet>();
				portal = new Portals();
				bomb = new Effects(); //for heros
				grenadeExplosion = new Effects(); //for effective particle damage
				fightGround = new Arena(false);
				laser = new Laser(fightGround);
				play = false;
				fightGround = new Arena(false);
				blueHero.setDx(0); blueHero.setDy(0);
				redHero.setDx(0); redHero.setDy(0);
				fightGround.protect();
				redHero.move(keep.get(4).x + keep.get(4).width-blueHero.width + 7, keep.get(4).y - blueHero.height -2);
				blueHero.move(keep.get(4).x + keep.get(4).width*3/2 + 40, keep.get(2).y - redHero.height -2);
				doctor.move(1250, keep.get(2).y - redHero.height -2); doctor.setDx(0);
				son.move(1250, keep.get(2).y - redHero.height -2); son.setDx(0);
				redDead = false;
				blueDead = false;
			}
			
			fightGround.moveAndDrawArena(win);
			
			
			
			
			
			if(fEG > 0 && !doneFading && !finaleFade){
				fadeColor = new Color(0, 0, 0, fEG*2);
				fEG --;
			}
			if(fEG == 10 && !doneFading){
				doneFading = true;
				redScore --;
				blueScore --;
				
			}
			
			KIA();
			preliminaryFunctions(win);
			
			if(doneFading){
				if(sEG >=1){rT.talk("Why did you do this, Blue");}
				if(sEG >=5){rT.mute(); bT.talk("Oh shut up,");}
				if(sEG >=7){bT.talk("Doctor");}
				if(sEG >=8){bT.mute();}
				if(sEG >=9){rT.talk("Impressive!");}
				if(sEG >=11){rT.mute(); redHero.setColor(Color.YELLOW); rT.setColor(Color.ORANGE);}
				if(sEG >=13){rT.talk("Was it really that easy to figure out?");}
				if(sEG >=16){bT.talk("And I take it that the real Red is dead then?"); rT.mute();}
				if(sEG >=20){bT.mute(); rT.talk("He was in the way so I had to...");}
				if(fEG == 20*25){ECS = true;}
				if(sEG >= 23){rT.talk("\"Deal\" with him");}
				if(sEG >= 25){rT.mute(); bT.talk("He was innocent");}
				if(sEG >=26){rT.talk("It doesn't matter now");}
				if(sEG >=28){rT.mute(); bT.mute();}
				if(sEG >=29){rT.talk("I'm going back");}
				if(sEG >=31){rT.talk("I'm going to fix all of");}
				if(sEG >=32){rT.mute();}
				if(sEG >=33){rT.talk("THIS");}
				if(sEG >=35){rT.talk("I'm going to stop me from creating you");}
				if(sEG >= 38){bT.talk("You can't!"); rT.mute();}
				if(sEG >= 40){bT.talk("I KILLED one of my clones!");}
				if(sEG >=42){bT.talk("I WON'T LET YOU ERASE THE OTHERS!");}
				if(sEG >=45){bT.talk("They're happy-");}
				if(sEG >=46){rT.talk("They're threats...");}
				if(sEG >= 47){bT.mute();}
				if(sEG >= 48){rT.mute(); bT.talk("How do you expect to get back?");}
				if(sEG >=51){bT.mute(); rT.talk("The portals that formed from the incident can send me back");}
				if(sEG >=54){rT.mute(); bT.talk("They can do that?");}
				if(sEG >=56){bT.mute(); rT.talk("With my alterations, yes");}
				if(sEG >=58){rT.mute();}
				if(sEG >=59){bT.talk("Why?");}
				if(sEG >=60){bT.talk("Why find me if you already know how to get back?");}
				if(sEG >=63){bT.mute();rT.talk("Because you are the missing link");}
				if(sEG >=65){rT.talk("You are the source");}
				if(sEG >=67){rT.talk("Do you think the portals formed because of that accident?");}
				if(sEG >=70){rT.talk("YOUR existence is tearing the fabric of space-time apart");}
				if(sEG >= 73){rT.talk("Those lasers are dimensional fractures CAUSED BY YOU");}
				if(sEG >= 76){rT.talk("I DIDN'T CREATE CLONES");}
				if(sEG >= 79){rT.talk("I CREATED A FOURTH-DIMENSIONAL BEING");}
				if(sEG >= 81){bT.talk("......"); rT.talk("I CREATED YOU");}
				if(sEG >= 83){bT.talk("Then....."); rT.mute();}
				if(sEG >=85){bT.talk("THEN WHO DID I KILL");}
				if(sEG >=87){bT.mute(); rT.talk("You're oscillating in and out of our timeline at random times");}
				if(sEG >=91){rT.talk("You still retain your three-dimensional memory so you haven't noticed");}
				if(sEG >= 95){rT.mute(); bT.talk("So I killed another version of");}
				if(sEG >= 97){bT.talk("ME?!");}
				if(sEG >= 98){bT.mute(); rT.talk("Precisely");}
				if(sEG >=99){rT.mute();}
				if(sEG == 99){portal.genPast(true);}
				if(sEG >=101){rT.talk("Well");}
				if(sEG >= 102){rT.talk("I must be going, thanks");}
				if(sEG >= 104){rT.mute();bT.talk("Wha- How...");}
				if(sEG >= 106){bT.mute(); rT.talk("You created it, remember?");}
				if(sEG >=108){rT.mute(); bT.talk("...........");}
				if(fEG == 108*25){redHero.setDx(-5);}
				if(sEG >= 110){bT.talk("WAIT");}
				if(sEG >=111){bT.mute();}
				if(fEG == 111*25){blueHero.setDx(-15);}
				
				//if(sEG == 0){sEG = 108; fEG = sEG*25;}
				
				if(fEG%25 == 0 && fEG != 0){sEG++;}
				fEG ++;
				
				if(redHero.x < 350 && !finaleFade){redHero.setColorGone(); redHero.setDx(0); if(portal.teleport(redHero)){teleport = true;}}
				if(blueHero.x < 350 && !finaleFade){
					blueHero.setDx(0);
					blueHero.setColorGone(); 
					portal.removePortals();
					if(portal.teleport(blueHero)){teleport = true;}
					if(!finaleFade){
						fF++;
					}
					if(fF < 52){
						whiteFade = new Color(255, 255, 255, 5*fF);
					}
					
					
					if(fF == 51){
						finaleFade = true;
						doneFading = false;
						fF = 81;
					}
				}
				
				

				
			//generates portal
			//blue tries to stop him
			//red moves left
			//blue shoots at him
			//red jump and deflects it with platform and kills
			//goes through portal	
			//stop clone outbreak from every happening		
					

				
				
				
				
			}
			if(finaleFade){
				if(fF > 0 && !startEnding){
					whiteFade = new Color(255, 255, 255, fF*3);
					fF --;
					redHero.setColorGone();
					blueHero.setColorGone();
					portal.genPast(false);
				}
				if(fF == 10 && !startEnding){
					startEnding = true;
					fF = 0;
					sF = 0;
					portal.genPast(false);
					blueHero.move(350, blueHero.y);
					redHero.setDx(0);
					blueHero.setDx(0);
					redHero.move(350, blueHero.y);
					doctor.move(1250, keep.get(2).y - redHero.height -2); doctor.setDx(0);
					son.move(1250, keep.get(2).y - redHero.height -2); son.setDx(0);
					whiteFade = new Color(0,0,0,0);
				}
				
				if(startEnding){
					redHero.setColor(Color.YELLOW);
					rT.setColor(Color.ORANGE);
					if(sF >= 0){redHero.setDx(13);}
					if(sF >= 1){rT.talk("Looks like the place"); redHero.setDx(0);}
					if(sF >=3){rT.talk("Now, I need to end this before it begins");}
					if(sF >=6){rT.mute(); redHero.setDx(5);}
					if(sF >=8){blueHero.setColor(Color.BLUE);}
					if(sF >=8){bT.talk("STOP"); }
					if(fF ==8*25){portal.forceDestroy(); portal.removePortals(); teleport = true;}
					if(sF >= 9){bT.mute(); blueHero.setDx(10);}
					if(sF >=10){blueHero.setDx(0);}
					if(sF >=11){rT.talk("You can't stop me, Blue");}
					if(sF >=13){redHero.setDx(5); rT.mute();}
					if(sF >= 14){bT.talk("Like Hell I can't");}
					if(sF >= 15){bT.mute();}
					if(fF == 15*25){portal.genStop(false); teleport = true;}
					if(fF >= 15*25 +10){blueHero.setDx(0);}
					if(fF >= 16*25 +10){rT.talk(".......");}
					if(sF >= 18){rT.talk("no.....");}
					if(sF >= 20){redHero.setDx(-15);rT.mute();}
					if(fF == 21*25){portal.genStop(true); teleport = true;}
					if(fF >= 21*25 + 20){redHero.setDx(0);rT.talk("NO!!!!!!");}
					if(fF >= 23){redHero.setDx(15);}
					if(fF >= 24){redHero.setDx(0);}
					if(sF >= 24){rT.talk("WHAT DID YOU JUST DO!");}
					if(sF >= 26){rT.mute();bT.talk("I can create portals");}
					if(sF >=28){bT.talk("REMEMBER");}
					if(sF >= 30){bT.mute(); rT.talk("YOU CAN'T DO THIS");}
					if(sF >=32){rT.talk("OUR UNIVERSE WILL COLLAPSE");}
					if(sF >= 34){rT.talk("WE'RE RUNNING OUT OF TIME");portal.teleport(redHero);}
					if(sF >=36){rT.mute(); bT.talk("I don't think you understand");}
					if(fF == 38*25){lastGrenade(blueHero,-20,-5);}
					if(sF >=38){bT.mute();}
					if(fF >= 38*25 +20){rT.talk("NO, PLEASE");}	
					if(fF >= 39*25 +10){redHero.setDx(-15); rT.mute();}
					//if(fF >=43*25-30){bT.talk("I");}
					//if(fF >=43*25-15){bT.talk("AM");}
					if(fF >=43*25){bT.talk("I AM TIME");}
					if(sF >= 40 && redDead){finalRedDead = true;}
					if(sF >= 40 && blueDead){finalBlueDead = true;}
					if(fF == 46*25){portal.expediteWait(1000);portal.removePortals();}
					if(sF >= 45){
						doctor.moveAndDrawHero(win, keep.get(0)); son.moveAndDrawHero(win, keep.get(0));
						doctor.heroCollision(keep.get(2)); son.heroCollision(keep.get(2));
					}
					if(sF >= 48){doctor.setDx(-21);}
					if(sF >=49){doctor.setDx(0);}
					if(fF >=49*25 +13){son.setDx(-5);}
					if(sF >=50){sT.talk("What is it?");}
					if(sF >=52){sT.mute(); son.setDx(0); dT.talk("I thought I heard something");}
					if(fF == 25*54){Setup = true;}
					if(sF >=54){dT.talk("Well,");}
					if(sF >=55){dT.talk("I guess it was nothing");}
					if(sF >=57){dT.mute(); sT.talk("Well when are we getting to your office?");}
					if(sF >= 59){sT.mute(); dT.talk("Just this way, Blue");}
					if(sF >= 60){dT.talk("And it's not an office");}
					if(sF >= 61){dT.talk("It's a laboratory");}
					if(sF >= 62){dT.mute();}
					if(sF >=63){dT.talk("Come on, I want to show you something cool");}
					if(sF >=66){dT.mute(); sT.talk("I'll race you there!");}
					if(sF >=68){sT.mute(); son.setDx(-15);}
					if(sF >=69){dT.talk("Don't get too far ahead, son");}
					if(sF >= 71){dT.talk("You wouldn't want to cause an accident");}
					if(sF >=73){dT.mute(); doctor.setDx(-10);}
		
					
					if(finalRedDead){
						redHero.setColorGone();
						rT.mute();
					}
					if(finalBlueDead){
						blueHero.setColorGone();
						bT.mute();
					}
					
					
					//if(sF == 0){sF = 38; fF = sF*25;}
					
					
					fF ++;
					sF = fF/25;
				}
			}
			
			
			
			if(finalBlueDead && finalRedDead && sF >= 73){
				win.setFont(new Font("Forte", Font.BOLD, 50));
				win.setColor(Color.MAGENTA);
				win.drawString("Um....so who won?", 300, 150);
				win.drawString("Press Space To Restart Game!", 300, 200);
				win.setFont(new Font("Verdana", Font.PLAIN, 20));
				win.setColor(Color.WHITE);
				win.drawString("(The Master Skip Combination for the Intro is [s][i] pressed simulataneously)", 225, 300);
				win.setFont(new Font("Forte", Font.BOLD, 50));
				win.setColor(Color.BLUE);
				win.drawString("" + (blueScore), 538, 395);
				win.setColor(Color.WHITE);
				win.drawString("-", 593, 395);
				win.setColor(Color.RED);
				win.drawString("" + (redScore), 613, 395);
				if(spacePressed){
					pO = 0;
					reset();
					music.stop(1);
				}
				rIncentive = false;
				bIncentive = false;
			}
			
			
			particleRemove();
			

			
			
			rT.getConvo();
			bT.getConvo();
			dT.getConvo();
			sT.getConvo();
			if(!finaleFade && !doneFading){
				win.setColor(fadeColor);
				//win.setColor(whiteFade);
			}else{
				win.setColor(whiteFade);
			}
			win.fill(fade);
		}
		
		
		
		if(pPressed && pO > 25 && !paused && !endGame){
			paused = true;
			pO = 0;
		}
		if(paused){
			fightGround.moveAndDrawArena(win);
			//drawExtra(win);
			win.setColor(pauseTrans);
			win.fill(pauseScreen);
			if(title){
				title = false;
				keep = 0;
			}else if(ins){
				ins = false;
				keep = 1;
			}else if(play){
				play = false;
				keep = 2;
			}else if(endGame){
				endGame = false;
				keep = 3;
			}else if(lastScene){
				lastScene = false;
				keep = 4;
			}
			
			//actual text
			win.setFont(new Font("Forte", Font.BOLD, 50));
			win.setColor(Color.GREEN);
			win.drawString("Instructions", 450, 125);
			win.setFont(new Font("Haettenschweiler", Font.BOLD, 30));
			win.setColor(Color.WHITE);
			win.drawString("Movement", 50, 225);
			win.drawString("Shoot:", 50, 305);
			win.drawString("Platform Generator", 50, 385);
			win.drawString("Grenades", 50, 465);
			win.setColor(Color.GREEN);
			win.setFont(new Font("Haettenschweiler", Font.PLAIN, 20));
			win.drawString("*Can jump when in contact with any platform", 50, 255);
			win.drawString("*Shoots in direction of your motion", 50, 335);
			win.drawString("*Shoots in direction OPPOSITE of your motion (when big bar full)", 50, 415);
			win.drawString("*Shoots in direction (when small bar full)", 50, 495);
			win.setColor(Color.BLUE);
			win.setFont(new Font("Cooper Black", Font.PLAIN, 30));
			win.drawString("[a(left), w(up), d(right)]", 400, 225);
			win.drawString("[q]", 560, 305);
			win.drawString("[s]", 560, 385);
			win.drawString("[e]", 560, 465);
			win.setColor(Color.RED);
			win.drawString("[j(left), i(up), l(right)]", 800, 225);
			win.drawString("[u]", 960, 305);
			win.drawString("[k]", 960, 385);
			win.drawString("[o]", 960, 465);
			
			
			
			
			
			if(pPressed && pO > 25){
				if(keep == 0){title = true; keep = -1;}
				if(keep == 1){ins = true; keep = -1;}
				if(keep == 2){play = true; keep = -1;}
				//if(keep == 3){endGame = true; keep = -1;}
				//if(keep == 4){lastScene = true; keep = -1;}
				paused = false;
				pO = 0;
			}
		}
		
		pO++;
		//
		
	}
	
	public void genBullet(Hero hero, ArrayList<SmartBullet> bullet, int bulletCnt){
		if(bullet.size() < 10 && bulletCnt > 20){
			bullet.add(new SmartBullet(hero.x  + (hero.width -7)/2, hero.y, 7, 7, hero.getColor()));
			double angle = Math.atan(hero.dy/hero.dx);
			SmartParticle p1 = bullet.get(bullet.size()-1);
			int speed = 17;
			if(hero.dx > 0){
				p1.setDx(speed*(Math.cos(angle)));
				p1.setDy(speed*Math.sin(angle));
			}else{
				p1.setDx(-speed*Math.cos(angle));
				p1.setDy(-speed*Math.sin(angle));
			}
			/*
			if(Math.pow(p1.getDx(), 2) + Math.pow(p1.getDy(),2) <= 1){
				p1.setDy(-15);
			}
			if(Math.abs(p1.getDx()) < 1 && Math.abs(p1.getDy()) < 1){
				p1.setDy(-15);
			}
			*/
			if(hero.dx ==0){
				if(hero.dy < 0){
					p1.setDy(-speed);
				}else{
					p1.setDy(speed);
				}
			}
			//p1.setDy(15*Math.sin(angle));
			if(hero == redHero){
				redBulletCnt = 0;
			}else{
				blueBulletCnt = 0;
			}
			this.bullet = true;
		}//throws bullet in block direction
	}
	
	public void killForced(Hero hero, ArrayList<SmartBullet> bullet, double dx, double dy){
		bullet.add(new SmartBullet(hero.x  + (hero.width -7)/2, hero.y, 7, 7, dx, dy, hero.getColor()));
		SmartParticle p1 = bullet.get(bullet.size()-1);
		this.bullet = true;
		
	}
	
	public void genBlock(Hero hero){
		block = true;
		SmartParticle p1 = new SmartParticle(hero.x + (hero.width-10)/2, hero.y + hero.height/2, 10, 10);
		if(hero.dx < 0){//goes opposite path of motion
			p1.setDx(15);
		}else if(hero.dx > 0){
			p1.setDx(-15);
		}else if(hero.dy < 0){//fix ground sensor
				p1.setDy(15);
		}else{
			p1.setDy(-15);
		}
		if(hero == redHero){
			p1.setColor(Color.MAGENTA);
		}else{
			p1.setColor(Color.CYAN);
		}
		additions.add(p1);
	}
	
	public void KIA(){
		for(int i = 0; i < redBullets.size(); i++){
			if(redBullets.get(i).intersects(blueHero) && !blueDead){
				heroDeath(blueHero);
			}//change to opposing color; changed
		}//for hero getting shot
		for(int i = 0; i < blueBullets.size(); i++){
			if(blueBullets.get(i).intersects(redHero) && !redDead){
				heroDeath(redHero);
			}//change to opposing color; changed
		}//for hero getting shot
		for(int i = 0; i < additions.size(); i++){
			SmartParticle p1 = additions.get(i);
			if(redHero.hitDetection(p1, (SmartParticle)redHero) && !redDead && p1.getColor().equals(Color.CYAN)){
				heroDeath(redHero);
			}
			if(blueHero.hitDetection(p1, (SmartParticle)blueHero) && !blueDead && p1.getColor().equals(Color.MAGENTA)){
				heroDeath(blueHero);
			}
		}//gen blocks can now kill too
		
		if(laser.heroContact(redHero) && !redDead){
			heroDeath(redHero);
		}//if laser hits the hero
		if(laser.heroContact(blueHero) && !blueDead){
			heroDeath(blueHero);
		}
		
		if(redDead){
			//bomb.moveExplosion(win);
			redDead = bomb.removeParticle();//removes particles and returns false when particles are all gone
		}
		if(blueDead){
			//bomb.moveExplosion(win);
			blueDead = bomb.removeParticle();//refer to above
		}
	}
	
	public void particleRemove(){
		bomb.removeParticle();//for platform collisions
		laser.removeParticle();//removes refleted particles
		portal.removeParticle();//removes suction particles
		//tears.removeParticle();
	}
	
	public void genGrenade(Hero hero){
		pin = true;
		SmartBullet p1 = new SmartBullet(hero.x + (hero.width-10)/2, hero.y + hero.height/2, 10, 10);
		double angle = Math.atan(hero.dy/hero.dx);
		int speed = 15;
		if(hero.dx > 0){
			p1.setDx(speed*(Math.cos(angle)));
			p1.setDy(speed*Math.sin(angle));
		}else{
			p1.setDx(-speed*Math.cos(angle));
			p1.setDy(-speed*Math.sin(angle));
		}	
		if(hero.dx ==0){
			if(hero.dy < 0){
				p1.setDy(-speed);
			}else{
				p1.setDy(speed);
			}
		}
		//Color redsGrenade = new Color(255, 0, 102);
		if(hero == redHero){
			p1.setColor(redsGrenade);
		}else{
			p1.setColor(bluesGrenade);
		}
		grenade.add(p1);
	}
	
	public void lastGrenade(Hero hero, double dx, double dy){
		pin = true;
		SmartBullet p1 = new SmartBullet(hero.x + (hero.width-10)/2, hero.y + hero.height/2, 10, 10);
		
				p1.setDy(dy);
			
				p1.setDx(dx);
			
		
		//Color redsGrenade = new Color(255, 0, 102);
		if(hero == redHero){
			p1.setColor(redsGrenade);
		}else{
			p1.setColor(bluesGrenade);
		}
		grenade.add(p1);
	}
	
	public void fragmentation(Graphics2D win){
		for(int i = 0; i < grenade.size(); i++){
			SmartBullet g = grenade.get(i);
			if(g.frag()){
				grenadeBoom = true;
				grenadeExplosion.genFragExplosion(grenade.remove(i));
			}
		}
		grenadeExplosion.moveGrenade(win);
		grenadeExplosion.removeGrenade();
		grenadeEffect();
	}
	
	public void grenadeEffect(){
		ArrayList<SmartParticle> keep = grenadeExplosion.getExplosion();
		ArrayList<SmartParticle> box = fightGround.getArena();
		for(int i = 0; i < keep.size(); i++){
			for(int j = 4; j < box.size(); j++){ //don't want to destroy base box
				
				if(bomb.hitDetection(box.get(j), keep.get(i)) && !box.get(j).getColor().equals(Color.GRAY)){
					bomb.genBoxExplosion(keep.get(i), box.remove(j)); //generates explosion and removes box component
					j--;
					
				}
			}
			if(blueHero.hitDetection(blueHero, keep.get(i)) && !blueDead){
				heroDeath(blueHero);
			}
			if(redHero.hitDetection(redHero, keep.get(i)) && !redDead){
				heroDeath(redHero);
			}
		}
	}
	
	public void heroDeath(Hero hero){
		ArrayList<SmartParticle> keep = fightGround.getArena();
		bomb.genExplosion(hero);
		if(hero == redHero){
			redDead = true;
			int k = (int)(Math.random()*3);
			if(k%2 == 0 || ins){
				redHero.move(this.getWidth()/2 - hero.width/2, this.getHeight()/2 - hero.height/2);
			}else{
				redHero.move((int)(Math.random()*900 + 100),(int)(Math.random()*400));
			}
			blueScore ++;
		}
		if(hero == blueHero){
			blueDead = true;
			int k = (int)(Math.random()*3);
			if(k%2 == 0 || ins){
				blueHero.move(this.getWidth()/2 - hero.width/2, this.getHeight()/2 - hero.height/2);
			}else {
				blueHero.move((int)(Math.random()*900 + 100),(int)(Math.random()*400));
			}
			redScore ++;
		}
		bulbBreak = true;
		//secondary = true;
	}//specify for blue
	
	
	public void genTowerExplosion(SmartParticle towerPart){
		towerBoom.add(new Effects());
		towerBoom.get(towerBoom.size() - 1).genExplosion(towerPart);
	}
	
	public void drawExtra(Graphics2D win){
		if(bluePowerReady.getWidth() < 200){
			SmartParticle temp = bluePowerReady;
			bluePowerReady.setBounds(temp.x, temp.y, (int)(temp.getWidth() + 5), (int)(temp.getHeight()));
		}//counter for plaform builder
		if(redPowerReady.getWidth() < 200){
			SmartParticle temp = redPowerReady;
			redPowerReady.setBounds(temp.x, temp.y, (int)(temp.getWidth() + 5), (int)(temp.getHeight()));
		}//counter for platform builder
		if(blueGReady.getWidth() < 200){
			SmartParticle temp = blueGReady;
			blueGReady.setBounds(temp.x, temp.y, (int)(temp.getWidth() + 1), (int)(temp.getHeight()));
		}//counter for grenade 
		if(redGReady.getWidth() < 200){
			SmartParticle temp = redGReady;
			redGReady.setBounds(temp.x, temp.y, (int)(temp.getWidth() + 1), (int)(temp.getHeight()));
		}//counter for grenade
		redHold.moveAndDraw(win);//loads ready status
		blueHold.moveAndDraw(win);
		redGHold.moveAndDraw(win);
		blueGHold.moveAndDraw(win);
		redPowerReady.moveAndDraw(win);//shows ready status
		bluePowerReady.moveAndDraw(win);
		redGReady.moveAndDraw(win);
		blueGReady.moveAndDraw(win);
		win.setFont(new Font("Forte", Font.PLAIN, 50));
		win.setColor(Color.RED);
		win.drawString("" + redScore, 850, 35);
		win.setColor(Color.BLUE);
		win.drawString("" + blueScore, 300, 35);
	}
	
	public void controls(){
		if(!redDead){
			redHero.moveHero(lPressed, jPressed);
			if(uPressed){
				genBullet(redHero, redBullets, redBulletCnt);
			}//spawns redBullet
			if(oPressed && redGReady.getWidth() == 200){
				redGReady.setBounds(redGReady.x, redGReady.y, 0, (int)redGReady.getHeight());
				genGrenade(redHero);
			}
			if(kPressed&& redPowerReady.getWidth() == 200){
				redPowerReady.setBounds(redPowerReady.x, redPowerReady.y, 0, (int)redPowerReady.getHeight());
				//resets power ready status
				genBlock(redHero); //generates walls by force
			}//spawns platform in accordance with color
		}//if not dead, move
		if(!blueDead){
			blueHero.moveHero(dPressed, aPressed);
			if(qPressed){
				genBullet(blueHero, blueBullets, blueBulletCnt);
			}//spawn blueBullet
			if(ePressed && blueGReady.getWidth() == 200){
				blueGReady.setBounds(blueGReady.x, blueGReady.y, 0, (int)blueGReady.getHeight());
				genGrenade(blueHero);
			}
			if(sPressed && bluePowerReady.getWidth() == 200){
				bluePowerReady.setBounds(bluePowerReady.x, bluePowerReady.y, 0, (int)bluePowerReady.getHeight());
				//resets power ready status
				genBlock(blueHero); //generates walls by force
			}//spawns platform in accordance with color
		}//if not dead, move
	}
	
	public void MAD(Graphics2D win){
		for(int i = 0; i < redBullets.size(); i++){
			portal.teleport(redBullets.get(i));
			redBullets.get(i).moveAndDraw(win);
		}	
		for(int i = 0; i < blueBullets.size(); i++){
			portal.teleport(blueBullets.get(i));
			blueBullets.get(i).moveAndDraw(win);
		}
		for(int i = 0; i < additions.size(); i++){
			if(portal.teleport(additions.get(i))){teleport = true;}
			additions.get(i).moveAndDraw(win);
		}
		for(int i = 0; i < grenade.size(); i++){
			if(portal.teleport(grenade.get(i))){teleport = true;}
			grenade.get(i).moveAndDrawGrenade(win);
		}
		portal.moveAndDrawPortals(win);
		bomb.moveExplosion(win);
		//tears.moveTears(win);
		fragmentation(win); //moves frag pieces
		laser.moveAndDrawLaser(win, fightGround);
		laser.moveReflection(win);
		fightGround.moveAndDrawArena(win);
		//portal.moveAndDrawPortals(win);
	}
	
	public void bye(){
		for(int i = 0; i < redBullets.size(); i++){
			if(redBullets.get(i).wornBullet()){
				redBullets.remove(i);
			}
		}//if bullet has reflect 10 times, it is erased on the sixth
		for(int i = 0; i < blueBullets.size(); i++){
			if(blueBullets.get(i).wornBullet()){
				blueBullets.remove(i);
			}
		}//if bullet has reflect 10 times, it is erased on the sixth
		if(portal.removePortals()){
			envPortalCnt = 0;
		}
	}
	
	public void preliminaryFunctions(Graphics2D win){
		ArrayList<SmartParticle> keep = fightGround.getArena();
		if(!redDead){redHero.moveAndDrawHero(win, keep.get(2));}
		if(!blueDead){blueHero.moveAndDrawHero(win, keep.get(2));}//moves hero
		MAD(win);
		//drawExtra(win);
		for(int i = 0; i < additions.size(); i++){
			if(fightGround.addPlat(additions.get(i))){
				additions.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < keep.size(); i++){
			redHero.heroCollision(keep.get(i));//hero is limited
			blueHero.heroCollision(keep.get(i));//hit detection
			for(int j = 0; j < redBullets.size(); j++){
				SmartBullet projectile = redBullets.get(j);
				if(redBullets.get(j).bulletCollision(keep.get(i))){//bullets reflect off walls
					if(keep.get(i).getColor().equals(Color.CYAN)){
						projectile.setColor(Color.CYAN);
						blueBullets.add(projectile);
						redBullets.remove(j);
						j--;
					}
				}//
			}//bullets reflect off walls
			for(int j = 0; j < blueBullets.size(); j++){
				SmartBullet projectile = blueBullets.get(j);
				if(blueBullets.get(j).bulletCollision(keep.get(i))){
					if(keep.get(i).getColor().equals(Color.MAGENTA)){
						projectile.setColor(Color.MAGENTA);
						redBullets.add(projectile);
						blueBullets.remove(j);
						j--;
					}
				}
			}//bullets reflect off walls
			for(int k = 0; k < grenade.size(); k++){
				SmartBullet p = grenade.get(k);
				p.bulletCollision(keep.get(i));
				p.bulletCollision(blueHero);
				p.bulletCollision(redHero);
			}
			grenadeExplosion.explodeParticleReturn(keep.get(i));//return opposite velocity from effect class
			bomb.explodeCollision(keep.get(i));
			//tears.explodeCollision(keep.get(i));
			laser.explodeParticleReturn(keep.get(i));
		}
	}
	
	//public void paint(Graphics g){
		//g.setColor(Color.WHITE);
		//g.drawOval(500, 200, 10, 10);
	//}
	
	public void reset(){
		grenade = new ArrayList<SmartBullet>();
		additions = new ArrayList<SmartParticle>();
		redBullets = new ArrayList<SmartBullet>();
		blueBullets = new ArrayList<SmartBullet>();
		portal = new Portals();
		bomb = new Effects(); //for heros
		grenadeExplosion = new Effects(); //for effective particle damage
		tutorialDone = false;
		fightGround = new Arena(false);
		laser = new Laser(fightGround);
		redBulletCnt = 0; //stops bullet spam
		blueBulletCnt = 0;
		envLaserCnt = 1; //for enviornment counter
		envPortalCnt = 0;
		portalRegulator = 100;
		laserRegulator = 400;//for laser spawn time
		laserFrequency = 1000; //increases laser spawn as match progresses
		redScore = 0;
		blueScore = 0;
		redDead = false; blueDead = false;;
		laserOnField = false; portalOnField = false;
		redHold = new SmartParticle(950, 20, 200, 10, Color.WHITE);
		blueHold = new SmartParticle(50, 20, 200, 10, Color.WHITE);
		redGHold = new SmartParticle(950, 32, 200, 5, Color.WHITE);
		blueGHold = new SmartParticle(50, 32, 200, 5, Color.WHITE);
		redPowerReady = new SmartParticle(950, 20, 10, 10, Color.RED);
		redGReady = new SmartParticle(950, 32, 10, 5, redsGrenade);
		bluePowerReady = new SmartParticle(50, 20, 10, 10, Color.BLUE);
		blueGReady = new SmartParticle(50, 32, 10, 5, bluesGrenade);
		play = false;
		title = true;
		endGame = false;
		ins = false;
		sEG =0;
		s = 0;
		tT = 0;
		fEG = 0;
		fF = 0;
		sF = 0;
		animex =0;
		animey = 0;
		switched = false;
		finalRedDead = false;
		finalBlueDead = false;
		cutSceneDone = false;
		doneFading = false;
		done = false;
		redWin = false;
		blueWin = false;
		redHero = new Hero(910,500, 20,20, Color.RED);
		blueHero = new Hero(290,500, 20,20, Color.BLUE);
		doctor = new Hero(1300, 550, 20, 20, Color.YELLOW);
		son = new Hero(1300,550, 20, 20, Color.BLUE);
		startEnding = false;
		finaleFade = false;
		lastScene = false;
		fadeColor = new Color(0, 0, 0, 0);
		whiteFade = new Color(255, 255, 255, 0);
		musicOrigin = true;
	}
	
	public void keyPressed (KeyEvent e){
		if(e.getKeyCode()== KeyEvent.VK_L){
			lPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_J){
			jPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_K){
			kPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_I){
			iPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_U){
			uPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_O){
			oPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_E){
			ePressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			aPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			dPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_W){
			wPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_Q){
			qPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			sPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			spacePressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_P){
			pPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			enterPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_N){
			nPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_M){
			mPressed = true;
		}
		
	}
	public void keyReleased (KeyEvent e){
		if(e.getKeyCode()== KeyEvent.VK_L){
			lPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_J){
			jPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_K){
			kPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_I){
			iPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_U){
			uPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_O){
			oPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_E){
			ePressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			aPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			dPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_W){
			wPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_Q){
			qPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			sPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			spacePressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_P){
			pPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			enterPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_N){
			nPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_M){
			mPressed = false;
		}
	}
}
