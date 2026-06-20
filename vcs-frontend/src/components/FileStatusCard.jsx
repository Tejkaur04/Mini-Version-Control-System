export default function FileStatusCard({
  title,
  files,
  color,
}) {
  return (
    <div
      className="
      bg-white
      rounded-2xl
      p-5
      shadow-sm
      "
    >
      <h2
        className={`font-semibold ${color}`}
      >
        {title}
      </h2>

      <p className="text-4xl font-bold mt-3">
        {files.length}
      </p>
    </div>
  );
}