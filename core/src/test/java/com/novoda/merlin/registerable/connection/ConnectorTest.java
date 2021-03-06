package com.novoda.merlin.registerable.connection;

import com.novoda.merlin.registerable.MerlinConnector;
import com.novoda.merlin.registerable.MerlinRegisterer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectorTest {

    private MerlinConnector<Connectable> merlinConnector;

    private Connector connector;

    @Before
    public void setUp() {
        merlinConnector = new MerlinRegisterer<>();
        connector = new Connector(merlinConnector);
    }

    @Test
    public void givenRegisteredConnectable_whenCallingOnConnect_thenCallsConnectForConnectable() {
        Connectable connectable = givenRegisteredConnectable();

        connector.onConnect();

        verify(connectable).onConnect();
    }

    @Test
    public void givenMultipleRegisteredConnectables_whenCallingOnConnect_thenCallsConnectForAllConnectables() {
        List<Connectable> connectables = givenMultipleRegisteredConnectables();

        connector.onConnect();

        for (Connectable connectable : connectables) {
            verify(connectable).onConnect();
        }
    }

    private List<Connectable> givenMultipleRegisteredConnectables() {
        List<Connectable> connectables = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Connectable connectable = mock(Connectable.class);
            connectables.add(connectable);
            merlinConnector.register(connectable);
        }
        return connectables;
    }

    private Connectable givenRegisteredConnectable() {
        Connectable connectable = mock(Connectable.class);
        merlinConnector.register(connectable);
        return connectable;
    }

}
