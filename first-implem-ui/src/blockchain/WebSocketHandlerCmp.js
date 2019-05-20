import React from 'react';
import Websocket from 'react-websocket';

class WebSocketHandlerCmp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            count: 90
        };

        const WebSocket = require('ws');

        let ws = new WebSocket('ws://locahost:8080/chat');

        ws.on('open', function open() {
            ws.send('something');
        });

        ws.on('message', function incoming(data) {
            console.log(data);
        });
    }



    render() {
        return (
            <div>
                Count: <strong>{this.state.count}</strong>


            </div>
        );
    }
}

export default WebSocketHandlerCmp;