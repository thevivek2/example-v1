import http from 'k6/http';
import {Counter, Rate, Trend} from 'k6/metrics';
import {sleep} from 'k6';

export let actual_http_req_duration = new Trend('actual_http_req_duration');
export let actual_http_reqs = new Counter('actual_http_reqs');
export let successful_requests = new Rate('successful_requests');

export const options = {
    stages: [
        {duration: '2s', target: 5000},
        {duration: '5s', target: 1000},
        {duration: '2s', target: 0},
    ],
};

function extracted() {
    let url = 'http://localhost:8282/api/v2/client';
    let header = getJsonHeader();
    let result = http.get(url, header);
    actual_http_req_duration.add(result.timings.duration);
    actual_http_reqs.add(1);
    /200/.test(result.status) ? successful_requests.add(true) : (successful_requests.add(false), logError(result));
}

export default function () {
    extracted();
    sleep(1)
}

export function getJsonHeader() {
    return {
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": "Basic VGhldml2ZWsyOjAxMjM0NTY3ODk="
        }
    }
}

export function logError(res) {
    console.warn(res.status, res.body);
}