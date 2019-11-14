import { DEFAULT_LOCALE, ACTION_TYPES, getMessages } from "./action";

export const localeIninitialState = {
  locale: DEFAULT_LOCALE,
  message: getMessages(DEFAULT_LOCALE),
};

export const reducer = (state = localeIninitialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.CHANGE_LOCALE:
      return { ...state, ...{ locale: action.payload.locale, message: getMessages(action.payload.locale) } };
    default:
      return state;
  }
};
