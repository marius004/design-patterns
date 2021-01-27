public abstract class Fighter {

	IKickStrategy kickStrategy;
	IJumpStrategy jumpStrategy;

	public Fighter(IKickStrategy kickStrategy, 
				   IJumpStrategy jumpStrategy) {
		this.kickStrategy = kickStrategy;
		this.jumpStrategy = jumpStrategy;
	}

	public void punch() {
		System.out.println("Default punch");
	}

	public void kick() {
		kickStrategy.kick();
	}

	public void jump() {
		jumpStrategy.jump();
	}

	public void roll() {
		System.out.println("Defualt roll");
	}

	public abstract void display();
}

interface IKickStrategy {
	void kick();
}

interface IJumpStrategy {
	void jump();
}

class LightningKickStrategy implements IKickStrategy {
	
	@Override
	public void kick() {
		System.out.println("Lightning Kick");
	}
}

class TornadoKickStrategy implements IKickStrategy {
	
	@Override
	public void kick() {
		System.out.println("Lightning Kick");
	}
}


class ShortJumpStrategy implements IJumpStrategy {
	
	@Override
	public void jump() {
		System.out.println("Short jump");
	}
}

class LongJumpStrategy implements IJumpStrategy {
	
	@Override
	public void jump() {
		System.out.println("Long jump");
	}
}

class Ryu extends Fighter {

	 public Ryu(IKickStrategy kickStrategy, 
	 			IJumpStrategy jumpStrategy) {
	 	super(kickStrategy, jumpStrategy);
	 }

	 public void display() {
	 	System.out.println("Ryu");
	 }
}

class Ken extends Fighter {

	 public Ken(IKickStrategy kickStrategy, 
	 			IJumpStrategy jumpStrategy) {
	 	super(kickStrategy, jumpStrategy);
	 }

	 public void display() {
	 	System.out.println("Ken");
	 }
}

class Main {

	public static void Main(string[] args) {

		IKickStrategy kickStrategy = new LightningKickStrategy();
		IJumpStrategy jumpStrategy = new LongJumpStrategy();

		Fighter fighter = new Ken(kickStrategy, jumpStrategy);

		fighter.punch();
		fighter.kick();
		fighter.jump();
	}

}