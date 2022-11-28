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
import pl.gda.pg.eti.kask.sa.behaviours.RequestAgentsListBehaviour;
import pl.gda.pg.eti.kask.sa.behaviours.RequestContainersListBehaviour;
import pl.gda.pg.eti.kask.sa.data.WelcomingMetadata;


public class MigratingAgent extends Agent {
    @Setter
    @Getter
    private List<Location> locations;
    
    @Setter
    @Getter
    private int jumpSeconds;
    
    @Getter
    private Map<AID, WelcomingMetadata> knownAgents;

    public MigratingAgent() {
    }

    @Override
    protected void setup() {
        super.setup();
        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());

        // Set the jump seconds accordingly.
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
           jumpSeconds = Integer.parseInt((String) args[0]);
        } else {
            Random rng = new Random();
            jumpSeconds = 2 + rng.nextInt(6);
        }

        knownAgents = new HashMap<AID, WelcomingMetadata>();
        this.addBehaviour(new RequestContainersListBehaviour(this));
    }

    @Override
    protected void afterMove() {
        super.afterMove();

        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());

        this.addBehaviour(new RequestAgentsListBehaviour(this));
    }

    @Override
    protected void beforeMove() {
        // Make sure the location is still there?
        super.beforeMove();
    }

    // Try welcoming a given agent.
    public void tryWelcoming(AID agent) {
        if (!knownAgents.containsKey(agent)) return;
        WelcomingMetadata metadata = knownAgents.get(agent);
        if (!metadata.isMigratingAgent) return;

        if (metadata.welcomeCooldown <= 0) {
            if (!metadata.welcomedInLocations.contains(here())) {
                metadata.welcomedInLocations.add(here());
                metadata.welcomeCooldown = 10;

                try {
                    ACLMessage welcomeMsg = new ACLMessage(ACLMessage.INFORM);
                    welcomeMsg.setContent("Hello from the kingdom of " + here().getName() + "!");
                    welcomeMsg.addReceiver(agent);
                    send(welcomeMsg);
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).warning("Failed to send a message to " + agent.getName());
                    Logger.getLogger(getClass().getName()).warning(e.getMessage());
                }
            }
        } else metadata.welcomeCooldown -= 1;

        knownAgents.put(agent, metadata);
    }
}