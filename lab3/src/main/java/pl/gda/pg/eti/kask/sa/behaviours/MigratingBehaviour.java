package pl.gda.pg.eti.kask.sa.behaviours;

import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import java.util.List;
import pl.gda.pg.eti.kask.sa.agents.MigratingAgent;

import java.util.Random;

public class MigratingBehaviour extends CyclicBehaviour {

    protected final MigratingAgent myAgent;
    
    public MigratingBehaviour(MigratingAgent agent) {
        super(agent);
        myAgent = agent;
    }

    @Override
    public void action() {
        List<Location> locations = myAgent.getLocations();
        if(locations != null) {
            Random rng = new Random();
            try {
                int locationIndex = rng.nextInt(locations.size());

                Location location = locations.get(locationIndex);
                //locations.remove(location);
                myAgent.doMove(location);
            } catch (IllegalArgumentException e) {

            }
        }
    }
}
