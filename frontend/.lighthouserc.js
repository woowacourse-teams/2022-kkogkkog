module.exports = {
    ci: {
      collect: {
        staticDistDir: "./build",
        numberOfRuns: 1,
      },
      upload: {
        target: "filesystem",
        outputDir: "./lhci_reports",
        reportFilenamePattern: "%%PATHNAME%%-%%DATETIME%%-report.%%EXTENSION%%",
      },
    },
  };
  