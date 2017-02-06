module.exports = function (req, res, next) {
  console.log("[Method] ", req.method);
  console.log("[Headers] ", req.headers);
  console.log("[Body] ", req.body);
  next();
}
