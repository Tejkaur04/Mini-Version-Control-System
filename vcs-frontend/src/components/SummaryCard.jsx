
export default function SummaryCard({
  title,
  value,
}) {
  return (
    <div
      className="
      bg-white
      rounded-3xl
      p-6
      shadow-sm
      border
      border-gray-100
    "
    >
      <p className="text-gray-500 text-sm">
        {title}
      </p>

      <h2 className="text-4xl font-bold mt-3">
        {value}
      </h2>
    </div>
  );
}