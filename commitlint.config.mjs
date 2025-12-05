export default {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'subject-case': [2, 'never', ['start-case', 'pascal-case', 'upper-case']],
    'header-max-length': [2, 'always', 300],
    'body-max-line-length': [2, 'always', 200], // <-- adicionada
  },
};
