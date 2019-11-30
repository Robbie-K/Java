import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


public class Tower{
	
	private Rectangle rectangle;
	private int ID;
	private int damage; //damage done to enemys
	private int type;
	private int price; //initial cost
	private int upgradePrice; // how much it cose to increase the level
	private int xCoord; // x pos on the map
	private int yCoord;//ypos on the map
	private int range;	//range of towers
	private int level = 0; // level, starts at 0, increments at 1
	private int cost; //total cost spent on towers
	private Pane pane;
	
	public int getUPrice() {return this.upgradePrice;}
	public int getDMG() {return this.damage;}
	public int getType() {return this.type;}
	public int getPrice() {return price;}
	public int getX() {return this.xCoord;}
	public int getY() {return this.yCoord;}
	public int getCost() {return this.cost;}
	public int getLevel() {return this.level;}
	public int getRange() {return this.range;}
	public Pane getPane() {return this.pane;}
	
	
	
	private void setPane(Pane pane) {
		this.pane = pane;
	}
	private void setUPrice(int price) {
		this.upgradePrice = price;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	private void setLevel() {
		this.level = getLevel() + 1;
	}

	private void setXCoord(int x) {
		this.xCoord = x;
	}
	
	private void setYCoord(int y) {
		this.yCoord = y;
	}
	
	private void setPrice(int price) {
		this.price = price;
	}
	
	private void setHP(int hp) {
		this.type = hp;
	}
	
	private void setDMG(int dmg) {
		this.damage = dmg;
	}
	

	
	private void setRange(int range) {
		this.range = range;
	}
	
	/**Constructor for tower
	 * 
	 * @param xc, xcoord, where tower is placed
	 * @param yc, ycoord
	 * @param price, intital price
	 * @param type, type of tower
	 * @param dmg, damage of tower
	 * @param range, range of tower
	 * @param filename, image for tower tpe
	 * @param canvas, pane that the game is plated on
	 * @param towerList, list of all towers
	 */
	public Tower(int xc, int yc, int price, int type, int dmg, int range, String filename, GridPane canvas, ArrayList<Tower> towerList)  {
		
		setXCoord(xc);
		setYCoord(yc);
		setPrice(price);
		setHP(type);
		setDMG(dmg);
		setLevel();
		setRange(range);
		setPane(canvas);
		setUPrice(getPrice() *2);
		setCost(price);
		
		System.out.println(type);
		
		Platform.runLater(new Runnable() {
			@Override 
			public void run() {
				rectangle = new Rectangle(35, 35); // makes a rectangle, size 35px by 35px
				
				GridPane.setConstraints(rectangle, xc, yc); 	//sets where the tower is placed
				GridPane.setHalignment(rectangle, HPos.CENTER);
				ImageLoader.setImage(filename, rectangle);
				canvas.getChildren().add(rectangle);
				
				Label label = new Label();
				
				//right click on towers for menu
				
		        rectangle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
		 
		            @Override
		            public void handle(ContextMenuEvent event) {
		            	ContextMenu contextMenu = new ContextMenu();
		            	
		            	//upgrade button
		            	MenuItem item1 = new MenuItem("Upgrade! (" + getUPrice() + ")");
		 		        item1.setOnAction(new EventHandler<ActionEvent>() {
		 		        	
		 		            @Override
		 		            public void handle(ActionEvent event) {
		 		                label.setText("Select Menu Item 1");
		 		                if ((TextGame.getMoney() - getUPrice()) >= 0) {
		 		                
		 		                	upgrade();
		 		                }
		 		            }
		 		        });
		 		        
		 		        //sell button
		 		        MenuItem item2 = new MenuItem("Sell! (" + getCost()/2 +")");
		 		        item2.setOnAction(new EventHandler<ActionEvent>() {
		 		 
		 		            @Override
		 		            public void handle(ActionEvent event) {
		 		                sell(towerList);
		 		            }
		 		        });
		            	
		 		        
		 		        // Stats button on the menu
		            	String statsString = "Level:            " + getLevel() + "\n" +
								 "Range:          " + getRange() + "\n" +
								 "Damage:       " + getDMG() + "\n" ;
		            	
		            	MenuItem stats = new MenuItem(statsString);
		            	contextMenu.getItems().addAll(stats,item1, item2);
		                contextMenu.show(rectangle, event.getScreenX(), event.getScreenY());
		            }
		        });
	
			}
		});

	}
	
	
	
	
	
	/**Upgrades the tower
	 * 
	 */
	public void upgrade() {
	
		setLevel();
		String filename = null;
		
		//diffrent image for each tower type
		if (getLevel() == 3) {
			if (type == 1) {
				filename = "tank1up.png";
			}else if (type == 2) {
				filename = "tank2up.png";
			}else if (type == 3) {
				filename = "tank3up.png";
			}else if (type == 4) {
				filename = "tank4up.png";
			}
			ImageLoader.setImage(filename, this.rectangle);
		}

		//spends money to upgrade
		TextGame.setMoney(TextGame.getMoney()-getUPrice());// cost money to upgrade
		setCost(getUPrice()); //updates total cost of tower
		setUPrice(2* getUPrice()); // next upgrade doubles in cost
		
		
		//upgrades increase range and damage of tower
		int newRange = (int) (getRange()*1.05); 
		setRange(newRange);
		int newDamage = (int) (getDMG()*1.1);
		setDMG(newDamage);

	}
	
	
	/**Sells the tower and deletes it from the pane, and list
	 * 
	 */
	public void sell(ArrayList<Tower> towerList) {
		
		TextGame.setMoney(TextGame.getMoney()+getCost()/2); //Adds have the cost of the tower to money

		TextGame.editGridTower(getY(),getX(),"#"); //removes tower from text version

		this.pane.getChildren().remove(this.rectangle); //removes tower from veiw
		towerList.remove(this); 
	}
	
	
	/**Check in range
	 * checks the list until the first enemy is in range, the does dmg to that enemy
	 * @param enemyList list of all the enemies on the board
	 */
	public void checkInRange(ArrayList<Enemy> enemyList) {

		Iterator<Enemy> iter = enemyList.iterator();
		while(iter.hasNext()) {
			Enemy enemy = (Enemy)iter.next();
			
			if(distanceFrom(enemy) < range) {
				Missles m1 = new Missles(this.pane, enemy, getX(),getY(), this.damage);
				return;
			}
			
		}
		
	}
	
	
	
	/**
	 * @param enemy, takes in a single enemy and finds the distance from a tower
	 * @return the distance from a tower
	 */
	public double distanceFrom(Enemy enemy) {
		double distance;
		double tx = this.xCoord * 50;
		
		double ty = this.yCoord * 50;
		double ex = enemy.getCircle().getTranslateX();
	
		double ey =  enemy.getCircle().getTranslateY();
		
		double x  = Math.abs(tx -ex);
		
		double y = Math.abs(ey - ty);
		
		distance = Math.sqrt(x*x + y*y);
	
		return distance;
	}
	
}
