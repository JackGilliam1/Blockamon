package blockamon_tests;

import blockamon.events.EventAggregator;
import blockamon.events.EventHandler;
import junit.framework.TestCase;

import java.lang.reflect.InvocationTargetException;

/**
 * User: Jack's Computer
 * Date: 1/20/14
 * Time: 12:17 PM
 */
public class EventAggregatorTests extends TestCase {

    public void testSubscriberReceivesEventData() throws InvocationTargetException, IllegalAccessException {
        TestEventHandler handler = new TestEventHandler();
        TestEvent event = new TestEvent();
        EventAggregator ea = new EventAggregator();
        ea.subscribe(handler);

        ea.broadcast(event);

        assertEquals(handler.getCount(), 1);
    }

    public void testUnSubscribersNoLongerReceiveEventData() throws InvocationTargetException, IllegalAccessException {
        TestEventHandler firstSubscriber = new TestEventHandler();
        TestEvent event = new TestEvent();
        TestEventHandler secondSubscriber = new TestEventHandler();
        EventAggregator ea = new EventAggregator();
        ea.subscribe(firstSubscriber);
        ea.subscribe(secondSubscriber);
        ea.unSubscribe(firstSubscriber);

        ea.broadcast(event);

        assertEquals(firstSubscriber.getCount(), 0);
        assertEquals(secondSubscriber.getCount(), 1);
    }

    public void testMultipleSubscribersOfSameTypeReceiveEventData() throws InvocationTargetException, IllegalAccessException {
        TestEventHandler firstSubscriber = new TestEventHandler();
        TestEventHandler secondSubscriber = new TestEventHandler();
        TestEvent event = new TestEvent();
        EventAggregator ea = new EventAggregator();
        ea.subscribe(firstSubscriber);
        ea.subscribe(secondSubscriber);

        ea.broadcast(event);

        assertEquals(firstSubscriber.getCount(), 1);
        assertEquals(secondSubscriber.getCount(), 1);
    }

    public void testMultiTypeSubscribersReceiveEventData() throws InvocationTargetException, IllegalAccessException {
        TestEventHandler firstTypeSubscriber = new TestEventHandler();
        AlternateTestEventHandler secondTypeSubscriber = new AlternateTestEventHandler();
        TestEvent event = new TestEvent();
        EventAggregator ea = new EventAggregator();
        ea.subscribe(firstTypeSubscriber);
        ea.subscribe(secondTypeSubscriber);

        ea.broadcast(event);

        assertEquals(firstTypeSubscriber.getCount(), 1);
        assertEquals(secondTypeSubscriber.getCount(), 1);
    }

    public void testOnlySubscribersOfAnEventReceiveEventData() throws InvocationTargetException, IllegalAccessException {
        TestEventHandler firstSubscriber = new TestEventHandler();
        AlternateTestEventHandler secondSubscriber = new AlternateTestEventHandler();
        SomeOtherTestEvent event = new SomeOtherTestEvent();
        EventAggregator ea = new EventAggregator();
        ea.subscribe(firstSubscriber);
        ea.subscribe(secondSubscriber);

        ea.broadcast(event);

        assertEquals(firstSubscriber.getCount(), 0);
        assertEquals(secondSubscriber.getCount(), 1);
    }

    public class AlternateTestEventHandler extends EventHandler {
        private int _count;

        public AlternateTestEventHandler(){
            _count = 0;
        }

        public void SomeOtherEventMethod(TestEvent event) {
            _count++;
        }

        public void SomeOtherEventTypeMethod(SomeOtherTestEvent event) {
            _count++;
        }

        public int getCount() {
            return _count;
        }
    }

    public class TestEventHandler extends EventHandler {

        private int _count;

        public TestEventHandler() {
            _count = 0;
        }

        public void EventMethod(TestEvent event){
            _count++;
        }

        public int getCount(){
            return _count;
        }
    }

    public class SomeOtherTestEvent {
    }

    public class TestEvent {
    }
}
