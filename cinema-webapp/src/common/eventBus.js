import Vue from 'vue';

const eventBus = new Vue();

const EVENT_LOGGED_IN = 'LOGGED_IN';

export { eventBus, EVENT_LOGGED_IN };
