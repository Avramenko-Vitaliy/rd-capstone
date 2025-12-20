import { createSlice, nanoid, PayloadAction } from '@reduxjs/toolkit';

export enum MessageType {
  SUCCESS = 'SUCCESS',
  INFO = 'INFO',
  WARNING = 'WARNING',
  ERROR = 'ERROR',
}

export interface Message {
  id: string;
  messageType: MessageType;
  message: string;
  details?: string;
}

export interface MessagesState {
  splashMessages: Message[];
}

const initialState: MessagesState = {
  splashMessages: [],
};

const messages = createSlice({
  name: 'messages',
  initialState,
  selectors: {
    selectMessages: state => state.splashMessages,
  },
  reducers: {
    showMessage: {
      reducer: (state, { payload: { id, messageType, details, message } }: PayloadAction<Message>): MessagesState => {
        const splashMessages =
          messageType === MessageType.ERROR
            ? state.splashMessages.filter(item => item.messageType !== MessageType.ERROR)
            : state.splashMessages;
        return { splashMessages: [{ id, messageType: messageType, details, message }, ...splashMessages] };
      },
      prepare: (message: string, messageType: MessageType = MessageType.ERROR, details?: string) => ({
        payload: {
          id: nanoid(),
          messageType,
          details,
          message,
        },
      }),
    },
    hideMessage: {
      reducer: (state, { payload: { id } }: PayloadAction<Partial<Message>>): MessagesState => {
        const splashMessages = state.splashMessages.filter(item => item.id !== id);
        return { splashMessages };
      },
      prepare: id => ({ payload: { id } }),
    },
  },
});

export const { selectors } = messages;
export const { showMessage, hideMessage } = messages.actions;
export default messages.reducer;
