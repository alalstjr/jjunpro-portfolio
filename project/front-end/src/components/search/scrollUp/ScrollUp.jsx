import React, {Component, Fragment} from 'react'
import ReactTransitionGroup from "react-addons-css-transition-group"
import SVG from "../../../static/svg/SVG"

import {ScrollUpBtn} from "../style"

class ScrollUp extends Component {
    constructor(props) {
        super(props);

        this.state = {
            scrollBtn: false
        }
    }

    componentDidMount() {
        window.addEventListener('scroll', this.onScroll);
    }

    componentWillUnmount() {
        window.removeEventListener('scroll', this.onScroll);
    }

    onScroll = () => {
        if (window.scrollY > 1) {
            this.setState({
                scrollBtn: true
            });
        } else {
            this.setState({
                scrollBtn: false
            });
        }
    }

    handleUp = () => {
        window.scrollTo(0, 0);
    }

    render() {

        const {scrollBtn} = this.state;

        return (
            <Fragment>
                <ReactTransitionGroup
                    transitionName={'Waring-anim'}
                    transitionEnterTimeout={200}
                    transitionLeaveTimeout={200}
                >
                    {
                        scrollBtn ?
                            <ScrollUpBtn onClick={this.handleUp}>
                                <SVG
                                    name={"arrowUp"}
                                    width="50px"
                                    height="50px"
                                    color={"#E71D36"}
                                />
                            </ScrollUpBtn>
                            :
                            null
                    }
                </ReactTransitionGroup>
            </Fragment>
        )
    }
}

export default ScrollUp;