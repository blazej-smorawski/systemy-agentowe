package pl.gda.pg.eti.kask.sa.behaviours;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Result;
import jade.core.Location;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.sa.agents.MigratingAgent;

@Log
public class ReceiveContainersListBehaviour extends Behaviour {
    private boolean done = false;
    protected final MigratingAgent myAgent;
    protected final String conversationId;
    protected MessageTemplate mt;

    public ReceiveContainersListBehaviour(MigratingAgent agent, String conversationId) {
        super(agent);
        myAgent = agent;
        this.conversationId = conversationId;
    }

    @Override
    public void onStart() {
        super.onStart();
        mt = MessageTemplate.MatchConversationId(conversationId);
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            done = true;
            try {
                ContentElement ce = myAgent.getContentManager().extractContent(msg);
                jade.util.leap.List items = ((Result) ce).getItems();
                List<Location> locations = new ArrayList<>();
                items.iterator().forEachRemaining(i -> {
                    locations.add((Location) i);
                });
                locations.remove(myAgent.here());
                myAgent.setLocations(locations);
            } catch (Codec.CodecException | OntologyException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean done() {
        return done;
    }

}
