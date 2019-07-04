import React, { Component } from 'react';
import { connect } from "react-redux";
import PropTypes from "prop-types";
 
import { 
    getBoardTasksCate, 
    getBoardTasks 
} from "../../../actions/boardTaskActions";

import { 
    CateGoryWrap,
    CateGoryUl 
} from "../style";
import { 
    Container 
} from "../../../style/globalStyles";

class CateGory extends Component {

    constructor(props) {
        super(props);

        this.state = {
            cateActive: 0
        }
    }

    // Category Active
    onClick = (cate) => {
        switch(cate) {
            case 0 : 
                this.props.getBoardTasks();        
                break;
            default :
                this.props.getBoardTasksCate(cate);        
                break;
        }

        this.setState({
            cateActive : cate
        });
    }

    render() {
        return (
            <CateGoryWrap>
                <Container>
                    <CateGoryUl>
                        <li
                            onClick={this.onClick.bind(this, 0)}
                            className={this.state.cateActive == 0 ? "active" : ""}
                        >
                            전체
                        </li>
                        <li 
                            onClick={this.onClick.bind(this, 1)}
                            className={this.state.cateActive == 1 ? "active" : ""}
                        >
                            진행중인 이벤트
                        </li>
                        <li
                            onClick={this.onClick.bind(this, 2)}
                            className={this.state.cateActive == 2 ? "active" : ""}
                        >
                            종료된 이벤트
                        </li>
                    </CateGoryUl>
                </Container>
            </CateGoryWrap>
        )
    }
}

CateGory.propTypes = {
    getBoardTasks: PropTypes.func.isRequired,
    getBoardTasksCate: PropTypes.func.isRequired,
    board_tasks: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    board_tasks: state.board_task
});

export default connect(
    mapStateToProps,
    { 
        getBoardTasksCate, 
        getBoardTasks
    }
)(CateGory);