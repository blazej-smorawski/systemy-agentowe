package pl.gda.pg.eti.kask.sa.behaviours;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import java.util.UUID;
import java.util.logging.Level;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.sa.agents.MigratingAgent;

@Log
public class RequestContainersListBehaviour extends OneShotBehaviour {
    protected final MigratingAgent myAgent;

    public RequestContainersListBehaviour(MigratingAgent agent) {
        super(agent);
        myAgent = agent;
    }

    @Override
    public void action() {
        QueryPlatformLocationsAction query = new QueryPlatformLocationsAction();
        Action action = new Action(myAgent.getAMS(), query);

        String conversationId = UUID.randomUUID().toString();

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setLanguage(new SLCodec().getName());
        request.setOntology(MobilityOntology.getInstance().getName());
        request.addReceiver(myAgent.getAMS());
        request.setConversationId(conversationId);

        try {
            myAgent.getContentManager().fillContent(request, action);
            myAgent.send(request);
            myAgent.addBehaviour(new ReceiveContainersListBehaviour(myAgent, conversationId));
        } catch (Codec.CodecException | OntologyException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}