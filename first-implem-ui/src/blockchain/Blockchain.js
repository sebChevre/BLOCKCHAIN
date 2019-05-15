import React, { Component } from 'react';
import axios from 'axios';
import Block from './Block';
import ReactJson from 'react-json-view'

class Blockchain extends Component {

    state = {
        blocks: []
    }

    componentDidMount() {
        axios.get(`/chain`)
        .then(response => {
            const blockchain = response.data;
            this.setState({ blocks: blockchain.blocks });
            console.log(this.state)
            console.log(this.state.blocks)
        })
    }

    render() {
        const bc = this.state.blockchain;
        //const arr = bc.blockchain.blocks;

        return (
            <div className="container-fluid">
                <div className="row">
                   <div className="col-8">
                    { this.state.blocks.map(block => {
                        return (
                            <Block key={block.hash} block={block} />

                        )
                    })}
                   </div>
                    <div className="col-4">
                        <div className="card">
                            <div className="card-body">
                                <h6 className="card-subtitle mb-2 text-muted">Structure Json</h6>
                                <ReactJson src={this.state.blocks} collapsed={true} />
                            </div>
                        </div>

                    </div>
                </div>
            </div>





        )
    }
}

export default Blockchain;