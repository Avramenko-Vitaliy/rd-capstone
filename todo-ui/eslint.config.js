import eslint from '@eslint/js'
import globals from 'globals'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import tseslint from 'typescript-eslint'
import stylistic from '@stylistic/eslint-plugin'

export default tseslint.config(
    { ignores: ['dist', 'node_modules'] },
    {
      extends: [eslint.configs.recommended, ...tseslint.configs.recommended],
      files: ['**/*.{ts,tsx}'],
      languageOptions: {
        ecmaVersion: 2020,
        parserOptions: {
          ecmaFeatures: {
            jsx: true,
          },
        },
        globals: {
          ...globals.browser,
          l: true,
          nl: true,
          sprintf: true,
        },
      },
      plugins: {
        '@stylistic': stylistic,
        'react-hooks': reactHooks,
        'react-refresh': reactRefresh.configs.vite,
      },
      rules: {
        ...reactHooks.configs['recommended-latest'].rules,
        '@stylistic/semi': 2,
        '@stylistic/no-extra-semi': 2,
        '@stylistic/quotes': ['error', 'single'],
        '@stylistic/member-delimiter-style': 'error',
        '@stylistic/jsx-max-props-per-line': [2, { maximum: 3 }],
        '@stylistic/jsx-indent-props': [2, 2],
        '@stylistic/jsx-indent': [2, 2],
        '@stylistic/comma-dangle': ['error' , 'always-multiline'],
        '@stylistic/dot-location': ['error', 'property'],
        '@stylistic/jsx-closing-tag-location': 1,
        '@stylistic/jsx-closing-bracket-location': 1,
        '@typescript-eslint/no-explicit-any': 'off',
        'react-hooks/exhaustive-deps': 'off',
        'react-hooks/preserve-manual-memoization': 'off',
        'react-hooks/set-state-in-effect': 'off',
      },
    },
)
