import React from "react"
import UniversityList from "../../university/list/UniversityList"
import { MainList } from "../../../style/globalStyles"

const FirstSection = ({searchPlaces, searchPlacesSetting, categorySearch}) => (
    <MainList>
      <UniversityList
        searchPlaces={searchPlaces}
        searchPlacesSetting={searchPlacesSetting}
        categorySearch={categorySearch}
      />
    </MainList>
);

export default FirstSection;