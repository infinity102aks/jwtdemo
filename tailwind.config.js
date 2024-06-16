/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      height: {
        'fit': 'calc(100vh - 150px)'
      },
      borderWidth: {
        '1': '1px'
      },
      backdropBlur: {
        'xs': "1px"
      }
    },
  },
  plugins: [],
}

