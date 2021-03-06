package com.novoda.merlin.registerable.disconnection;

import com.novoda.merlin.registerable.MerlinConnector;
import com.novoda.merlin.registerable.MerlinRegisterer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DisconnectorTest {

    private MerlinConnector<Disconnectable> merlinDisconnector;

    private Disconnector disconnector;

    @Before
    public void setUp() {
        initMocks(this);
        merlinDisconnector = new MerlinRegisterer<>();
        disconnector = new Disconnector(merlinDisconnector);
    }

    @Test
    public void givenRegisteredDisconnectable_whenCallingOnDisconect_thenCallsDisconnectForDisconnectable() {
        Disconnectable disconnectable = givenRegisteredDisconnectable();

        disconnector.onDisconnect();

        verify(disconnectable).onDisconnect();
    }

    @Test
    public void givenMultipleRegisteredDisconnectables_whenCallingOnConnect_thenCallsConnectForAllDisconnectables() {
        List<Disconnectable> disconnectables = givenMultipleRegisteredDisconnectables();

        disconnector.onDisconnect();

        for (Disconnectable disconnectable : disconnectables) {
            verify(disconnectable).onDisconnect();
        }
    }

    private List<Disconnectable> givenMultipleRegisteredDisconnectables() {
        List<Disconnectable> disconnectables = new ArrayList<>(3);

        for (int disconnectableIndex = 0; disconnectableIndex < disconnectables.size(); disconnectableIndex++) {
            Disconnectable disconnectable = mock(Disconnectable.class);
            disconnectables.add(disconnectable);
            merlinDisconnector.register(disconnectable);
        }

        return disconnectables;
    }

    private Disconnectable givenRegisteredDisconnectable() {
        Disconnectable disconnectable = mock(Disconnectable.class);
        merlinDisconnector.register(disconnectable);
        return disconnectable;
    }

}
