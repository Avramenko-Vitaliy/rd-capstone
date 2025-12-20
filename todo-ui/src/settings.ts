import { ParseOptions } from 'query-string';

interface Settings {
  limit: number;
  timeout: number;
  messageDelay: number;
  searchDelay: number;
  queryFormat: ParseOptions;
}

const settings: Settings = {
  limit: 10,
  timeout: 1000 * 60,
  messageDelay: 1000 * 5,
  searchDelay: 800,
  queryFormat: {
    arrayFormat: 'bracket-separator',
    arrayFormatSeparator: '|',
  },
};

export default settings;
