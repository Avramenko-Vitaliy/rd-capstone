import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';
import eslint from '@nabla/vite-plugin-eslint';
import path from 'path';
import compression from 'vite-plugin-compression';
import { visualizer } from 'rollup-plugin-visualizer';

export default defineConfig(({ mode }) => {
  const viteEnv = loadEnv(mode, process.cwd(), 'VITE_');
  const allEnv = loadEnv(mode, process.cwd(), '');

  const allowedNonViteVars = ['NODE_ENV', 'API_URL', 'PUBLIC_URL'];
  const env = {
    ...viteEnv,
    ...Object.fromEntries(
        Object.entries(allEnv)
            .filter(([key]) => allowedNonViteVars.includes(key)),
    ),
    // Always include the current mode
    MODE: mode,
  };
  return {
    plugins: [
      react({
        reactRefreshHost: 'http://localhost:9000',
      }),
      eslint({
        eslintOptions: {
          cache: false,
        },
        shouldLint: (path: string) => !/node_modules/.test(path),
      }),
      mode === 'production' && (compression as any)({
        algorithm: 'gzip',
        ext: '.gz',
      }),
      // Add bundle size analyzer in development mode
      mode === 'development' && visualizer({
        open: false,
        gzipSize: true,
        brotliSize: true,
        filename: 'dist/stats.html',
      }),
    ].filter(Boolean),
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src'),
      },
    },
    server: {
      port: 9000,
      host: true,
      proxy: {
        '/api': {
          target: 'http://localhost:8080',
          changeOrigin: true,
          secure: false,
        },
      },
    },
    define: {
      'process.env': env,
    },
    build: {
      outDir: 'dist',
      sourcemap: mode === 'development',
      minify: mode === 'production' ? 'esbuild' : false,
      chunkSizeWarningLimit: 1000,
      cssCodeSplit: true,
      target: 'es2020',
      assetsInlineLimit: 4096,
      assetsInclude: ['**/*.css'],
      rollupOptions: {
        output: {
          assetFileNames: (assetInfo) => {
            const ext = assetInfo.names.pop() || '';
            if (/css/i.test(ext)) {
              return 'css/[name]-[hash].[ext]';
            }
            if (/png|jpe?g|svg|gif|tiff|bmp|ico/i.test(ext)) {
              return 'img/[name]-[hash].[ext]';
            }
            if (/js/i.test(ext)) {
              return 'js/[name]-[hash].[ext]';
            }
            return '[name]-[hash].[ext]';
          },
          chunkFileNames: 'js/[name]-[hash].js',
          entryFileNames: 'js/[name]-[hash].js',
          manualChunks: {
            'react': ['react', 'react-dom', 'react-router'],
            'mui': [
              '@emotion/react',
              '@emotion/styled',
              '@mui/icons-material',
              '@mui/material',
              '@mui/styled-engine-sc',
              '@mui/x-date-pickers',
              'styled-components',
              'tss-react',
            ],
            'form': ['react-final-form', 'final-form'],
            'redux': ['react-redux', '@reduxjs/toolkit'],
            'network': ['axios'],
            'utils': ['lodash', 'dayjs'],
          },
        },
      },
      commonjsOptions: {
        transformMixedEsModules: true,
      },
    },
    preview: {
      port: 9000,
      host: true,
    },
    esbuild: {
      logOverride: { 'this-is-undefined-in-esm': 'silent' },
      drop: mode === 'production' ? ['console', 'debugger'] : [],
    },
    optimizeDeps: {
      include: [
        'react',
        'react-dom',
        '@mui/material',
        '@emotion/react',
        '@emotion/styled',
        'react-router-dom',
        '@reduxjs/toolkit',
        'react-redux',
        'final-form',
        'react-final-form',
      ],
    },
  };
});
