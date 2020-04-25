package org.nefele.home;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.HashMap;

public class NefeleResizer {
	
   private final HashMap<Cursor, EventHandler<MouseEvent>> listener = new HashMap<>();
   private final Stage stage;
   private final Scene scene;
   private final int TR;
   private final int TM;
   private final double SCREEN_WIDTH, SCREEN_HEIGHT;
	
   private double mPresSceneX, mPresSceneY;
   private double mPresScreeX, mPresScreeY;
   private double mPresStageW, mPresStageH;
	
   private boolean mIsMaximized = false;
   private double mWidthStore, mHeightStore, mXStore, mYStore;
	  
   private boolean isDragged = true;
	
	
   public NefeleResizer(Stage stage, int dt, int rt) { // dt = draggable area (in px) , rt = resizable area (in px)
		  
		    this.TR = rt;
		    this.TM = dt + rt;
		    this.stage = stage;
		    this.scene = stage.getScene();
		
		    this.SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
		    this.SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
		
		    createListener();
		    launch();
	}
	
   public void close() {
	      System.exit(0);
   }
	
   public void minimize() {
		  stage.setIconified(true);
   }
	
	
   public void switchWindowedMode(ImageView resizeIcon) {
		  
	    if (mIsMaximized) {
	    	
			stage.setY(mYStore);
			stage.setX(mXStore);
			stage.setWidth(mWidthStore);
			stage.setHeight(mHeightStore);
			//resizeIcon.setImage(new Image("images/shapeLight.png")); change resize icon
	    } 
	    else {
	    	
			mXStore = stage.getX();
			mYStore = stage.getY();
			mWidthStore = stage.getWidth();
			mHeightStore = stage.getHeight();
			//resizeIcon.setImage(new Image("images/restoreDownLight.png"));
			stage.setY(0);
			stage.setX(0);
			stage.setWidth(SCREEN_WIDTH);
			stage.setHeight(SCREEN_HEIGHT);
	    }
	    
	    	mIsMaximized = !mIsMaximized;
   }
	
   private void createListener() {
		  
	    listener.put(Cursor.NW_RESIZE, event -> {
		
		      double newWidth = mPresStageW - (event.getScreenX() - mPresScreeX);
		      double newHeight = mPresStageH - (event.getScreenY() - mPresScreeY);
		      if (newHeight > stage.getMinHeight()) {
		    	  stage.setY(event.getScreenY() - mPresSceneY);
		    	  stage.setHeight(newHeight);
		      }
		      if (newWidth > stage.getMinWidth()) {
		    	  stage.setX(event.getScreenX() - mPresSceneX);
		    	  stage.setWidth(newWidth);
		      }
	    });
		
	    listener.put(Cursor.NE_RESIZE, event -> {
		
		      double newWidth = mPresStageW - (event.getScreenX() - mPresScreeX);
		      double newHeight = mPresStageH + (event.getScreenY() - mPresScreeY);
		      if (newHeight > stage.getMinHeight()) stage.setHeight(newHeight);
		      if (newWidth > stage.getMinWidth()) {
		    	  stage.setX(event.getScreenX() - mPresSceneX);
		    	  stage.setWidth(newWidth);
		      }
	    });
		
	    listener.put(Cursor.SW_RESIZE, event -> {
		
		      double newWidth = mPresStageW + (event.getScreenX() - mPresScreeX);
		      double newHeight = mPresStageH - (event.getScreenY() - mPresScreeY);
		      if (newHeight > stage.getMinHeight()) {
		    	  stage.setHeight(newHeight);
		    	  stage.setY(event.getScreenY() - mPresSceneY);
		      }
		      if (newWidth > stage.getMinWidth()) stage.setWidth(newWidth);
	    });
		
	    listener.put(Cursor.SE_RESIZE, event -> {
		      double newWidth = mPresStageW + (event.getScreenX() - mPresScreeX);
		      double newHeight = mPresStageH + (event.getScreenY() - mPresScreeY);
		      if (newHeight > stage.getMinHeight()) stage.setHeight(newHeight);
		      if (newWidth > stage.getMinWidth()) stage.setWidth(newWidth);
	    });
		
	    listener.put(Cursor.E_RESIZE, event -> {
		      double newWidth = mPresStageW - (event.getScreenX() - mPresScreeX);
		      if (newWidth > stage.getMinWidth()) {
		    	  stage.setX(event.getScreenX() - mPresSceneX);
		    	  stage.setWidth(newWidth);
		      }
	    });
		
	    listener.put(Cursor.W_RESIZE, event -> {
		      double newWidth = mPresStageW + (event.getScreenX() - mPresScreeX);
		      if (newWidth > stage.getMinWidth()) stage.setWidth(newWidth);
	    });
		
	    listener.put(Cursor.N_RESIZE, event -> {
		      double newHeight = mPresStageH - (event.getScreenY() - mPresScreeY);
		      if (newHeight > stage.getMinHeight()) {
		    	  stage.setY(event.getScreenY() - mPresSceneY);
		    	  stage.setHeight(newHeight);
		      }
	    });
		
	    listener.put(Cursor.S_RESIZE, event -> {
		      double newHeight = mPresStageH + (event.getScreenY() - mPresScreeY);
		      if (newHeight > stage.getMinHeight()) stage.setHeight(newHeight);
	    });
		
	    listener.put(Cursor.DEFAULT, event -> {
		    	stage.setX(event.getScreenX() - mPresSceneX);
		    	stage.setY(event.getScreenY() - mPresSceneY);
	    });
   }
	
   private void launch() {
	
	  scene.setOnMousePressed(event -> {
	      mPresSceneX = event.getSceneX();
	      mPresSceneY = event.getSceneY();
	
	      mPresScreeX = event.getScreenX();
	      mPresScreeY = event.getScreenY();
	
	      mPresStageW = stage.getWidth();
	      mPresStageH = stage.getHeight();
	 });
		
	 scene.setOnMouseMoved(event -> {
	      double sx = event.getSceneX();
	      double sy = event.getSceneY();
	
	      boolean l_trigger = sx > 0 && sx < TR;
	      boolean r_trigger = sx < scene.getWidth() && sx > scene.getWidth() - TR;
	      boolean u_trigger = sy < scene.getHeight() && sy > scene.getHeight() - TR;
	      boolean d_trigger = sy > 0 && sy < TR;
	
	      if (l_trigger && d_trigger) fireAction(Cursor.NW_RESIZE);
	      else if (l_trigger && u_trigger) fireAction(Cursor.NE_RESIZE);
	      else if (r_trigger && d_trigger) fireAction(Cursor.SW_RESIZE);
	      else if (r_trigger && u_trigger) fireAction(Cursor.SE_RESIZE);
	      else if (l_trigger) fireAction(Cursor.E_RESIZE);
	      else if (r_trigger) fireAction(Cursor.W_RESIZE);
	      else if (d_trigger) fireAction(Cursor.N_RESIZE);
	      else if (sy < TM && !u_trigger) {
	    	  isDragged=true;
	    	  fireAction(Cursor.DEFAULT);
	      }
	      else if (u_trigger) fireAction(Cursor.S_RESIZE);
	      else fireAction(Cursor.DEFAULT);
	  });
   }
	
   private void fireAction(Cursor c) {
		  
		scene.setCursor(c);
		 
	    if(c == Cursor.DEFAULT && isDragged) scene.setOnMouseDragged(listener.get(c));
	    else if (c != Cursor.DEFAULT) scene.setOnMouseDragged(listener.get(c));
	    else scene.setOnMouseDragged(null);
	    
	    isDragged=false;
    }

}
