import React, { Component } from 'react';
import axios from 'axios';
import Block from './Block';
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
            console.log(this.state.blockchain.blocks)
        })
    }

    render() {
        const bc = this.state.blockchain;
        //const arr = bc.blockchain.blocks;

        return (
            <div>
                { this.state.blocks.map(block => {
                    return (
                        <Block key={block.hash} hash={block.hash} />

                    )
                })}
            </div>



        )
    }
}

export default Blockchain;