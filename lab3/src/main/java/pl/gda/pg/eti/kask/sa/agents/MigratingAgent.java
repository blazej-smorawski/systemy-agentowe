package main.java.pl.gda.pg.eti.kask.sa.agents;

import jade.core.Agent;

public class MigratingAgent extends Agent {
    @Override
    protected void setup() {
    super.setup();
    //register languages
    //register ontologies
    //add behaviours
    }
    @Override
    protected void afterMove() {
    super.afterMove();
    //restore state
    //resume threads
    }
    @Override
    protected void beforeMove() {
    //stop threads
    //save state
    super.beforeMove();
    }
}