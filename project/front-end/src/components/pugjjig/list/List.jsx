import React, { Component, Fragment } from "react"
import Item from "../../../components/pugjjig/list/item/Item"
import { PugjjigItemWrap } from "../style"

class List extends Component {

    handleLikeUpdate = (preData, postData) => {
        let props;
        
        preData.map(preData => {
            // id 가 일치하면 변경되는 값만 찾아서 변경합니다.
            if(preData.key*1 === postData.id) {
               props = preData.props;
               props.pugjjig.uniLike = postData.uniLike;
               props.pugjjig.uniLikeState = postData.uniLikeState;
            }
        });
    } 

    render() {
        // props Init
        const { 
            pugjjigLike, 
            pugjjig_list, 
            pugjjig_like 
        } = this.props;

        // Variables Init
        let pugjjigContent;
        let pugjjigList = [];
        
        // pugjjigList
        const pugjjigGet = (pugjjig) => {
            if(pugjjig.data !== undefined) {
                const data = pugjjig.data.map(pugjjig => (
                    <Item 
                        key = {pugjjig.id}
                        pugjjig = {pugjjig}
                        pugjjigLike = {pugjjigLike}
                    />
                ));

                for(let i = 0; i < data.length; i++) {
                    pugjjigList.push(data[i]);
                }
                
                // 푹찍 Like 클릭시 Re rendering 여부 체크
                if(pugjjig_like.data !== undefined) {
                    this.handleLikeUpdate(pugjjigList, pugjjig_like.data);
                }

                return (
                    <Fragment>
                        {pugjjigList}
                    </Fragment>
                );
            } else {
                return (
                    <div>푹찍이 존재하지 않습니다.</div>
                );
            }
        }

        // pugjjig Get List View
        pugjjigContent = pugjjigGet(pugjjig_list);

        return (
            <PugjjigItemWrap>
                {pugjjigContent}
            </PugjjigItemWrap>
        )
    }
}

export default List;