# Dash

A dashboard for Mindfork.

# Hacking Dash

You'll need [Leiningen](http://leiningen.org/).

To get started working with Dash, you should be working in the `tests` build.

First, fire up the [Ring](https://github.com/weavejester/lein-ring) testing backend:

```bash
lein ring server-headless
```

Then start up the local dev server using figwheel:

```bash
lein figwheel test
```

Direct your browser to [`localhost:3449/index_test.html`](http://localhost:3449/index_test.html).  Happy hacking!
