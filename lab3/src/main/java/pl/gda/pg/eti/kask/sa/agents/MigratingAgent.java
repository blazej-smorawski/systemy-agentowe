package pl.gda.pg.eti.kask.sa.agents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.sa.behaviours.MigratingBehaviour;
import pl.gda.pg.eti.kask.sa.behaviours.RequestContainersListBehaviour;


public class MigratingAgent extends Agent {
    @Setter
    @Getter
    private List<Location> locations;
    
    @Setter
    @Getter
    private int jumpSeconds;

    public MigratingAgent() {
    }

    @Override
    protected void setup() {
        super.setup();
        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());
        this.addBehaviour(new RequestContainersListBehaviour(this));
        this.addBehaviour(new MigratingBehaviour(this));
    }

    @Override
    protected void afterMove() {
        super.afterMove();

        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());

        try{
            Thread.sleep(3000);
        } catch (InterruptedException e){

        }
    }

    @Override
    protected void beforeMove() {
        // Make sure the location is still there?
        super.beforeMove();
    }

}