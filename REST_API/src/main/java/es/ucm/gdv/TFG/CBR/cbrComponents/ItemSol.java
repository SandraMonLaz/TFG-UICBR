package es.ucm.gdv.TFG.CBR.cbrComponents;


//Clase que representa a un item como solucion en el CBR
public class ItemSol {
	
	public ItemSol(ScreenPos screenPosition, Scale itemScale, String image, ItemId id) {
		super();
		this.screenPosition = screenPosition;
		this.itemScale = itemScale;
		this.image = image;
		this.id = id;
	}

	public enum ScreenPos {
		TOP_LEFT,
		TOP_CENTER,
		TOP_RIGHT,
		MIDDLE_LEFT,
		MIDDLE_CENTER,
		MIDDLE_RIGHT,
		BOTTOM_LEFT,
		BOTTOM_CENTER,
		BOTTOM_RIGHT
	}
	
	public enum Scale {
		VERY_SMALL,
		SMALL,
		MEDIUM,
		BIG,
		VERY_BIG
	}
	

	private ScreenPos screenPosition;
	private Scale itemScale;
	private String image;
	private ItemId id;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ItemId getId() {
		return id;
	}

	public void setId(ItemId id) {
		this.id = id;
	}
	
	public ScreenPos getScreenPosition() {
		return screenPosition;
	}
	
	public void setScreenPosition(ScreenPos screenPosition) {
		this.screenPosition = screenPosition;
	}
	
	public Scale getItemScale() {
		return itemScale;
	}
	
	public void setItemScale(Scale itemScale) {
		this.itemScale = itemScale;
	}
	
	public void fromString(String content) {
		String[] splited = content.split("-");
		id = ItemId.valueOf(splited[0]);
		this.screenPosition = ScreenPos.valueOf(splited[1]);
		this.itemScale = Scale.valueOf(splited[2]);
		this.image = splited[3];
	}
	
	@Override
	public String toString() {
		return id + "-" + screenPosition + "-" + itemScale + "-" + image + "-";
	}
}
