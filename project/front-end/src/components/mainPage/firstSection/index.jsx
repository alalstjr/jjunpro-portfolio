import React from "react"
import UniversityList from "../../university/list/UniversityList"
import { MainList, MainListContainer } from "../../../style/globalStyles"

const FirstSection = ({
  searchPlaces, 
  searchPlacesSetting, 
  categorySearch,
  initSearch,
  hendleInitSearch
}) => (
    <MainList
      initSearch={initSearch}
    >
      <MainListContainer>
        <UniversityList
          searchPlaces={searchPlaces}
          searchPlacesSetting={searchPlacesSetting}
          categorySearch={categorySearch}
          hendleInitSearch={hendleInitSearch}
          initSearch={initSearch}
        />
      </MainListContainer>
    </MainList>
);

export default FirstSection;