import React from "react"
import { useLocalState } from "../index"

export const Test = () => {
    const [a1, a2] = useLocalState("asdadddddddddddddddddddddddddddd");
    return(
        <div>
            {a1}
            <button onClick={() => a2("안녕하세요")}>click</button>
        </div>
    );
}