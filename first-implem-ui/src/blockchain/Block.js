import React, { Component } from 'react';

class Block extends Component {

    render() {
        return (
        <div className="card block">
            <div className="card-header">
                <span>{this.props.hash}</span>
            </div>
            <div class="card-body">
                <h5 class="card-title">Special title treatment</h5>
                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
            </div>
        </div>
        )
    }
}

export default Block;