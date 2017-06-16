package cards;

import java.util.ArrayList;

import main.Game;

public class Troop extends Entity
{
	protected boolean[] abilities = new boolean[6]; //in this order: {provoke, deflect, blast, range, mirror, void}
	protected boolean deflectTime, mirrorTime;
	protected int currentApCost; //for the speed boost gear
	protected static ArrayList<Gear> gearArray = new ArrayList<Gear>(); 

	public Troop(int tag) 
	{
		super(tag);
		
		if(abilities[1] == true)
			deflectTime = true;
		else
			deflectTime = false;
		
		if(abilities[4] == true)
			mirrorTime = true;
		else
			mirrorTime = false;
		
		currentApCost = apCost;
	}
	

	/**
	 * @param abs - the index of the ability i.e. 1 for deflect, or 5 for void (see above)
	 * @return boolean - whether or not the ability was added
	 */
	public boolean addAbilities(int abs)
	{
		if(abs == 1)
			deflectTime = true; // in order to reset the abilities each turn, you just have to addAbilites(1 or 4) at the beginning of the phase
		if(abs == 4)
			mirrorTime = true;
		
		if(abilities[abs] == false)
		{
			if((abilities[2] == true || abilities[3] == true) && (abs == 2 || abs == 3)) // makes sure you dont have blast and range
				return false;
			abilities[abs] = true;
			return true;
		}
		return false;
	}

	public boolean canDeflect()
	{
		if(deflectTime == true)
		{
			deflectTime = false;
			return true;
		}
		return false;
	}
	
	public boolean canMirror()
	{
		if(mirrorTime == true)
		{
			mirrorTime = false;
			return true;
		}
		return false;
	}
	
	public boolean hasAbility(int num)
	{
		return abilities[num];
	}
	
	public void attack(Entity defender)
	{
		boolean canAttack = true;
		
		if(abilities[3] == false && Math.abs(defender.getPosX() - xCoordinate) == 1 && Math.abs(defender.getPosY() - yCoordinate) == 1)
			return; //if not in range, don't attack (also cant attack self)
		
		for(int i = xCoordinate - 1; i < xCoordinate + 1; i++)
		{
			for(int j = yCoordinate - 1; j < yCoordinate + 1; j++)
			{
				if((i != xCoordinate && j != yCoordinate) && Game.game.getEntityAt(i,j)!= null && Game.game.getEntityAt(i,j).hasAbility(0) == true)
				{				
					canAttack = false; //checks to see if there is any provoke troops adjacent to attacker
				}
			}
		}

		if(canAttack == false) // if something had provoke...
		{
			if(Math.abs(defender.getPosX() - xCoordinate) <= 1 && Math.abs(defender.getPosY() - yCoordinate) <= 1 && defender.hasAbility(0))
			{  //if the defender is in range, and has provoke. attack it
				if(abilities[2]) //but if it has blast, also attack everything else in row/col up until void troop
				{
					blastAttack(defender);	
				}
				else if(abilities[0] || abilities[1] || abilities[3] || abilities[4])
				{
					dealDamage(defender);
				}
			}
		}
		else //if there is nothing with provoke, you can just attack 
		{
			if(abilities[2])  //if it has blast attack everything else in row/col up until void troop
			{
				blastAttack(defender);
			}
			else if(abilities[0] || abilities[1] || abilities[3] || abilities[4])
			{
				dealDamage(defender);
			}
		}
	}

	public void kill(Entity killed) 
	{
		Game.game.addToGraveyard(killed);
		Game.game.getBoard()[killed.xCoordinate][killed.yCoordinate] = null;
		
		for(int i = gearArray.size(); i > 0; i --)
		{
			if(gearArray.get(i) != null)
			{
			Game.game.addToGraveyard(new Gear(gearArray.get(i).getGearEnum()));
			gearArray.remove(i);
			}
		}
	}
	
	public int getCurrentApCost() {
		return currentApCost;
	}

	public void changeApCost(int ap) {
		currentApCost += ap;
	}
	
	public boolean[] getAbilities() {
		return abilities;
	}
	
	public static int placeOnBoard(Troop placed, int cp)
	{
		if(cp < placed.getCpCost() || Game.game.getEntityAt(placed.getPosX(), placed.getPosY()) != null)
			return cp;
		Game.game.placeEntity(placed, placed.getPosX(), placed.getPosY());
		return cp - placed.getCpCost();
	}
	
	public static void equipGear(Gear gear)
	{
		gearArray.add(gear);
	}
	
	public void blastAttack(Entity defender)
	{
		int rowChange = 0;
		int colChange = 0;
		if(defender.getPosX() > xCoordinate)
			rowChange = 1;
		if(defender.getPosX() < xCoordinate)
			rowChange = -1;
		if(defender.getPosY() > yCoordinate)
			colChange = 1;
		if(defender.getPosY() < yCoordinate)
			colChange = -1;
		//checking to see where in relation the defender is too attacker (direction wise)
		
		if(rowChange != 0 && colChange != 0)
			return; //this means that the defender is diagonal, and you cant attack diagonal, so dont attack
		
		for(int row = xCoordinate; row < 15 && row > -1; row += rowChange)
		{
			for(int col = yCoordinate; col < 15 && col > -1; col += colChange)
			{
				if(Game.game.getEntityAt(row,col)!= null)
				{
					if(Game.game.getEntityAt(row,col).hasAbility(5) == false)
					{
						dealDamage(defender);
					}
					else
					{
						row = 15;
						col = 15;
					}
				}
			}
		}
	}
}

