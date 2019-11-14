import React, { Component } from 'react';
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

import { ImgBox, WrapActive } from "../../../../style/globalStyles";
import { ItemWrap, ItemBox, ItemTitle, ItemContent } from "../../style";

class Item extends Component {
    constructor(props) {
        super(props);

        this.state = {
            mouseState:false    
        };
    }
    render() {
        // props Init
        const { board_task } = this.props;

        // Variables Init
        let timer = 0;

        const funcActive = () => {
            this.setState({
                mouseState : true
            });
            
        }
    
        const onMouseDown = (e) => {
            timer = setTimeout(funcActive, 1000);
        }
        const onMouseUp = () => {
            clearTimeout(timer);
        }
        const onMouseLeave = () => {
            clearTimeout(timer);

            this.setState({
                mouseState : false
            });
        }

        return (
            <ItemWrap
                onMouseDown={onMouseDown}
                onMouseUp={onMouseUp}
                onMouseLeave={this.state.mouseState === true ? null : onMouseLeave}
            >
                <Link
                    to={`/boardEvent/view/${board_task.num}`}
                    onClick={this.state.mouseState === true ? e=>e.preventDefault() : null}
                    className={this.state.mouseState === true ? "active" : null}
                >
                    <WrapActive>
                        <ImgBox 
                            bgImg={board_task.thumb}
                            bgSize={"90%"}
                        />
                        <ItemBox>
                            <ItemTitle>{board_task.writer}</ItemTitle>
                            <ItemContent>{board_task.content}</ItemContent>
                        </ItemBox>
                    </WrapActive>
                </Link>
            </ItemWrap>
        )
    }
}

Item.propTypes = {
    board_task: PropTypes.object.isRequired
}

export default Item;