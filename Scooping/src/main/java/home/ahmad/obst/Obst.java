package home.ahmad.obst;

public class Obst {
	private String name;
	private String bootstrapIcon;

	public Obst(String name, String bootstrapIcon) {
		this.name = name;
		this.bootstrapIcon = bootstrapIcon;
	}

	public String getName() {
		return name;
	}

	public String getBootstrapIcon() {
		return bootstrapIcon;
	}
}
