package com.guzzler13.mareunion.di;


import com.guzzler13.mareunion.service.DummyMeetingApiService;
        import com.guzzler13.mareunion.service.MeetingApiService;


/**
 * Dependency injector to get instance of services
 */
public class DI {
    private static final MeetingApiService serviceMeeting = new DummyMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     */
    public static MeetingApiService getMeetingApiService() {
        return serviceMeeting;
    }


    /**
     * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}