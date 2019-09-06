package com.furnituren.denemefurniture;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.parseColor;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private ModelRenderable couchRenderable,
            modelRenderable,
            pianoRenderable;

    ImageView couch,model,piano;
    String TAG = "HaTa";

    View arrayView[];
    ViewRenderable name_furniture;
    ViewRenderable furniture_name;
    int selected =1; // model is choosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        model = (ImageView) findViewById(R.id.model);
        piano = (ImageView) findViewById(R.id.piano);
        couch = (ImageView) findViewById(R.id.couch);

        setArrayView();
        setClickLisstener();
        
        setupModel();
        
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());
                
                createModel(anchorNode,selected);
            }
        });

    }

    private void setupModel() {

        ViewRenderable.builder()
                .setView(this,R.layout.name_furniture)
                .build()
                .thenAccept(renderable -> name_furniture = renderable);
        ModelRenderable.builder()
                .setSource(this, R.raw.model)
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Log.e(TAG, "Unable to load Renderable.", throwable);
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.couch)
                .build()
                .thenAccept(renderable -> couchRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Log.e(TAG, "Unable to load Renderable.", throwable);
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.piano_01)
                .build()
                .thenAccept(renderable -> pianoRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Log.e(TAG, "Unable to load Renderable.", throwable);
                            return null;
                        });
    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if(selected ==1){
            TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
            model.setParent(anchorNode);
            model.setRenderable(modelRenderable);
            model.select();

            addName(anchorNode,model,"Model");
        }
        else if(selected ==2){
            TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
            model.setParent(anchorNode);
            model.setRenderable(couchRenderable);
            model.select();

            addName(anchorNode,model,"Couch");
        }
        else if(selected ==3){
            TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
            model.setParent(anchorNode);
            model.setRenderable(pianoRenderable);
            model.select();

            addName(anchorNode,model,"Piano");
        }
    }

    private void addName(AnchorNode anchorNode, TransformableNode model, String name) {
        TransformableNode nameView = new TransformableNode(arFragment.getTransformationSystem());
        nameView.setLocalPosition(new Vector3(0f,model.getLocalPosition().y+0.5f,0));
        nameView.setParent(anchorNode);
        nameView.setRenderable(name_furniture);
        nameView.select();

        TextView txtname = (TextView)name_furniture.getView();
        txtname.setText(name);
        txtname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anchorNode.setParent(null);
            }
        });

    }

    private void setClickLisstener() {
        for(int i =0;i<arrayView.length;i++){
            arrayView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId() == R.id.model){
                        selected=1;
                        setBackground(v.getId());
                    }
                    else if (v.getId() == R.id.couch) {
                        selected=2;
                        setBackground(v.getId());
                    }
                    else if(v.getId()== R.id.piano){
                        selected=3;
                        setBackground(v.getId());
                    }
                }
            });
        }
    }

    private void setBackground(int id) {
        for(int i =0;i<arrayView.length;i++){
           if(arrayView[i].getId()==id){
               arrayView[i].setBackgroundColor(parseColor("#80333698"));
           }else{
               arrayView[i].setBackgroundColor(TRANSPARENT);
           }
        }
    }


    private void setArrayView() {
        arrayView = new  View[]{
                model,couch,piano
        };
    }

    private void addModeltoScene(Anchor anchor, ModelRenderable modelRenderable){
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }

}
